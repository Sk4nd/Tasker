package it.skand.tasker;

import it.skand.tasker.resources.DummyBrightnessActivity;
import it.skand.tasker.resources.Weather;
import it.skand.tasker.resources.WeatherAPI;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class EventReceiver extends BroadcastReceiver {
	Calendar c;
	AudioManager mAudioManager;
	IntentFilter ifilter;
	Intent batteryStatus;
	Context context;
	GPS gps;
	private Weather weather;
	private double temp;
	private static double pioggia=0;
	private static double nuvole=0;
	//Database
	private MyOpenHelper oh;
	private DataSource db;
	
	//costanti controllo
	private static final int ORA=1;
	private static final int GIORNO=2;
	private static final int MESE=3;
	private static final int ANNO=4;
	private static final int BATTERIA=5;
	private static final int GPS=6;
	private static final int TEMPO=7;
	private static final int TEMPERATURA=8;
	
	//costanti azioni
	private static final int NORMALE=1;
	private static final int VIBRAZIONE=2;
	private static final int SILENZIOSO=3;
	private static final int LUMINOSITA_LOW=4;
	private static final int LUMINOSITA_MED=5;
	private static final int LUMINOSITA_HIG=6;
	private static final int SEND_SMS=7;
	private static final int SEND_TOAST=8;
	private static final int NOTIFICA=9;

	//Receiver per gli eventi (alarmmanager) che il servizio controllera in automatico
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context=context;
		
		//inizializza le variabili locali
		gps=new GPS(context);
		mAudioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		c=Calendar.getInstance();
		ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, ifilter);
		oh=new MyOpenHelper(context);
    	db=new DataSource(oh);
    	
    	
        
		try {
			//prende bundle dati passati dal servizio
			Bundle bundle = intent.getExtras();
			int tipoControllo=bundle.getInt("tipoControllo");
			int numEvento=bundle.getInt("numEvento");
			controlloC (bundle, tipoControllo, numEvento);			
		} catch (Exception e) {
			Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	//FUNZIONE SMISTAMENTO CONTROLLI
	public void controlloC (Bundle bundle, int tipoControllo, int numEvento) {
		
		switch (tipoControllo) {
		
			case ORA: case GIORNO: case MESE: case ANNO: {
				int ora=bundle.getInt("ora");
				int minuti=bundle.getInt("minuti");
				int giorno=bundle.getInt("giorno");
				int mese=bundle.getInt("mese");
				int anno=bundle.getInt("anno");
				int tipoAzione=bundle.getInt("tipoAzione");
				
				if (tipoControllo==ORA) { //se devo controllare l'ora
					if (controlloOrario(ora, minuti)) { //se il controllo e positivo
						eseguiAzione(tipoAzione, numEvento);
					}
				}
				else if (tipoControllo==GIORNO) { //Giorni
					if (controlloOrario(giorno, ora, minuti)) {
						eseguiAzione(tipoAzione, numEvento);
					}
				}
				else if (tipoControllo==MESE) { //mesi
					if (controlloOrario(giorno, mese, ora, minuti)) {
						eseguiAzione(tipoAzione, numEvento);
					}
				}
				else if (tipoControllo==ANNO) { //anno
					if (controlloOrario(giorno, mese, anno, ora, minuti)) {
						eseguiAzione(tipoAzione, numEvento);
					}
				}
				break;
			}
			
			case BATTERIA: {
				int tipoAzione=bundle.getInt("tipoAzione");
				int livello=bundle.getInt("livello");
				if (controlloBatteria(batteryStatus, livello)) {
					eseguiAzione(tipoAzione, numEvento);
				}
				break;
			}
			
			case GPS: {
				int tipoAzione=bundle.getInt("tipoAzione");
				double lat=bundle.getDouble("lat");
				double lon=bundle.getDouble("lon");
				if (controlloGPS(lat,lon)) {
					eseguiAzione(tipoAzione, numEvento);
				}
				break;
			}
			
			case TEMPO: {
				int tipoAzione=bundle.getInt("tipoAzione");
				String condizione=bundle.getString("condizione");
				fetchWeather();
				if (controlloWeather(condizione)) {
					eseguiAzione(tipoAzione, numEvento);
				}
				break;
			}
			
			case TEMPERATURA: {
				int tipoAzione=bundle.getInt("tipoAzione");
				String condizione=bundle.getString("condizione");
				int temperatura=bundle.getInt("temp");
				fetchWeather();
				if (controlloTemperatura(condizione, temperatura)) {
					eseguiAzione(tipoAzione, numEvento);
				}
				break;
			}
			
			default: break;
		}
		
	}
	
	//FUNZIONE CONTROLLO AZIONE DA ESEGUIRE
	public void eseguiAzione (int tipoAzione, int numEvento) {
		
		switch (tipoAzione) {
		
			case NORMALE: case VIBRAZIONE: case SILENZIOSO: {
				suoneria(tipoAzione);
				break;
			}
			
			case LUMINOSITA_LOW: {
				luminosita(0.1f);
				break;
			}
			
			case LUMINOSITA_MED: {
				luminosita(0.5f);
				break;
			}
			
			case LUMINOSITA_HIG: {
				luminosita(1f);
				break;
			}
			
			case SEND_SMS: {
				String testo=db.cercaTesto(numEvento);
				String num=db.cercaNumero(numEvento);
				sendSMS(num, testo);
				break;
			}
			
			case SEND_TOAST: {
				String testo=db.cercaTesto(numEvento);
				Toast.makeText(context, testo, Toast.LENGTH_LONG).show();
				break;
			}
			
			case NOTIFICA: {
				String testo=db.cercaTesto(numEvento);
				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(context)
				        .setSmallIcon(R.drawable.ic_launcher)
				        .setContentTitle("TASKER")
				        .setContentText(""+testo);
				mBuilder.setLights(-256, 500, 500);
				//da aggiungere setSound per far suonare il suono che vogliamo
				NotificationManager mNotificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(numEvento, mBuilder.setAutoCancel(true).build());
			}
			
			default: break;
		}
	}
	
	
	
	//funzioni per il controllo delle condizioni sugli eventi
	
	//controllo orario/calendario
	//data
	public boolean controlloOrario (int giorno, int mese, int anno, int ora, int minuti) {
		int giorn=c.get(Calendar.DAY_OF_MONTH);
		int mes=c.get(Calendar.MONTH);
		int ann=c.get(Calendar.YEAR);
		int or=c.get(Calendar.HOUR_OF_DAY);
		int minut=c.get(Calendar.MINUTE);
		if (giorn==giorno && mes==mese && ann==anno && or==ora && minut==minuti) {
			return true;
		}
		else
			return false;
	}
	
	//mese
	public boolean controlloOrario (int giorno, int mese, int ora, int minuti) {
		int giorn=c.get(Calendar.DAY_OF_MONTH);
		int mes=c.get(Calendar.MONTH);
		int or=c.get(Calendar.HOUR_OF_DAY);
		int minut=c.get(Calendar.MINUTE);
		if (giorn==giorno && mes==mese && or==ora && minut==minuti) {
			return true;
		}
		else
			return false;
	}
	
	//giorno
	public boolean controlloOrario (int giorno, int ora, int minuti) {
		int giorn=c.get(Calendar.DAY_OF_MONTH);
		int or=c.get(Calendar.HOUR_OF_DAY);
		int minut=c.get(Calendar.MINUTE);
		if (giorn==giorno && or==ora && minut==minuti) {
			return true;
		}
		else
			return false;
	}
	
	//orario
	public boolean controlloOrario (int ora, int minuti) {
		int or=c.get(Calendar.HOUR_OF_DAY);
		int minut=c.get(Calendar.MINUTE);
		if (or==ora && minut==minuti) {
			return true;
		}
		else
			return false;
	}
	
	//controllo batteria
	public boolean controlloBatteria (Intent batteryStatus, int level) {
		if (batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)*100/batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, 100) <= level) {
			return true;
		}
		else
			return false;
	}
	
	//controllo gps
	public boolean controlloGPS (double lat, double lon) {
		if (gps.canGetLocation()) {
			if (distFrom(lat, lon, gps.getLatitude(), gps.getLongitude())<=100) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	//controllo tempo atmosferico
	public boolean controlloWeather(String condizione) {
		if (condizione.equalsIgnoreCase("nuvoloso")) {
			if (nuvole>=20)
				return true;
			else
				return false;
		}
		else if (condizione.equalsIgnoreCase("piove")) {
			if (pioggia>=1)
				return true;
			else
				return false;
		}
		else {
			if (nuvole<20)
				return true;
			else
				return false;
		}
	}
	
	public boolean controlloTemperatura(String condizione, int temperatura) {
		if (condizione.equals("maggiore")) {
			if (temp>temperatura)
				return true;
			else
				return false;
		}
		else if (condizione.equals("minore")) {
			if (temp<temperatura)
				return true;
			else
				return false;
		}
		else {
			if (temp<temperatura+1 && temp>temperatura-1)
				return true;
			else
				return false;
		}
	}
	
	
	
	//funzioni per l'esecuzione delle azioni
	
	//cambio tipo suoneria
	public void suoneria (int tipo) {		//1 normale 2 vibrazione 3 silenzioso
		if (tipo==NORMALE) {
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
		else if (tipo==VIBRAZIONE) {
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		else {
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}		
	}
	
	//cambio luminosita schermo
	public void luminosita (float tipo) {
		//conversione da float a int
		int brightnessInt = (int)(tipo*255);

		//Controlla se la luminosita settata e 0, cosa che spegnerebbe lo schermo
		if(brightnessInt<1) {
			brightnessInt=1;
		}

		//Setta impostazioni di luminosita del sistema 
		Settings.System.putInt(context.getContentResolver(),
		Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);

		//Applico la luminosita selezionata con un'activity fittizia
		Intent intent = new Intent(context, DummyBrightnessActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("brightness value", tipo); 
		context.startActivity(intent);
	}
	
	//Invio sms; quando chiami questa funzione, cerca nel database all'evento corrispondente numero e testo da inviare
	public void sendSMS (String numero, String testo) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(numero, null, testo, null, null);
	}
	
	
	/////////////////////FUNZIONI UTILITY
	
	//funzione per calcolare distanza tra due punti gps
	public static double distFrom(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        return dist * meterConversion;
    }

	public void fetchWeather() {
		new FetchWeatherTask().execute();
	}
	
	//async task per fetchare il meteo
	private class FetchWeatherTask extends AsyncTask<Void, Void, Weather> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Weather doInBackground(Void... params) {			
			try {
				JSONObject jsonResult = new JSONObject(WeatherAPI.getWeather(gps.getLatitude(), gps.getLongitude()));
				weather=new Weather(jsonResult);
			} catch (Exception e) {
				Log.e("tasker", e.getMessage());
				weather = null;
			}
			return weather;
		}
		
		@Override
		protected void onPostExecute(Weather weather) {
			if(weather!=null) {
				pioggia=weather.getRain();
				nuvole=weather.getClouds();
				temp=weather.getTemperatura();
			}
			super.onPostExecute(weather);
		}
	}
}
