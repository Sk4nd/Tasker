package it.skand.tasker;
//TODO implementa il not

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.Calendar;

import android.media.AudioManager;
import android.content.Context;
import android.database.Cursor;

public class Servizio extends Service {
	
	AudioManager mAudioManager;
	Calendar c;
	Service a=this;
	int tipoSuoneria=0;
	int NUM_EVENTI=0;
	Evento[] eventi;
	IntentFilter ifilter;
	Intent batteryStatus;
	Context context=this;
	
	//costanti controllo
	private static final int ORA=1;
	private static final int GIORNO=2;
	private static final int MESE=3;
	private static final int ANNO=4;
	private static final int BATTERIA=5;
	private static final int GPS=6;
	private static final int TEMPO=7;
	private static final int TEMPERATURA=8;
	//TODO
	
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
	//TODO
	private static final int NULL=0;
	
	private static final int RICHIESTA=11;
	
	//Database
	private MyOpenHelper oh;
	private DataSource db;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
	
		// TODO Scrivi tutti i listener necessari al servizio
		mAudioManager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
		c=Calendar.getInstance();
		ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = this.registerReceiver(null, ifilter);
		
		oh=new MyOpenHelper(this);
    	db=new DataSource(oh);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Scrivi tutte le azioni da compiere in base agli eventi
		
		
		//Prende tutti gli eventi nel db e li salva in un array
		NUM_EVENTI=db.contaEventi();
		eventi=new Evento[NUM_EVENTI];
		Cursor cu=db.lista();
		int j=0;
		while (cu.moveToNext()) {
			eventi[j]=new Evento(cu.getInt(cu.getColumnIndex("tipocontrollo")), cu.getInt(cu.getColumnIndex("tipoazione")),
					cu.getString(cu.getColumnIndex("var1")), cu.getString(cu.getColumnIndex("var2")), cu.getString(cu.getColumnIndex("var3")),
					cu.getString(cu.getColumnIndex("var4")), cu.getString(cu.getColumnIndex("var5")));
			j++;
		}
		
		//puo essere messo in una funzione
		for (int i=0; i<NUM_EVENTI; i++) {
			
			if (eventi[i].getTipoControllo()==ORA || eventi[i].getTipoControllo()==GIORNO ||
						eventi[i].getTipoControllo()==MESE || eventi[i].getTipoControllo()==ANNO) {
							
				controlloOrario(eventi[i].getTipoAzione(), i, eventi[i].getTipoControllo(), 
															Integer.parseInt(eventi[i].var1), Integer.parseInt(eventi[i].var2),
															NULL, NULL, NULL //Manca controllo se valore passato=null, mettilo a 0, 
															//quindi ho messo null a mano
															);
															
			}
			
			else if (eventi[i].getTipoControllo()==BATTERIA) {
				controlloBatteria(eventi[i].getTipoAzione(), i, eventi[i].getTipoControllo(), Integer.parseInt(eventi[i].var1));
			}
			//43.10534280849572, 12.347720861434937 casa			
			else if (eventi[i].getTipoControllo()==GPS) {
				controlloGPS(eventi[i].getTipoAzione(), i, eventi[i].getTipoControllo(), Double.parseDouble(eventi[i].var1), 
						Double.parseDouble(eventi[i].var1));
			}
			else if (eventi[i].getTipoControllo()==TEMPO) {
				controlloWeather(eventi[i].getTipoAzione(), i, eventi[i].getTipoControllo(), eventi[i].var1);
			}
			else if (eventi[i].getTipoControllo()==TEMPERATURA) {
				controlloTemperatura(eventi[i].getTipoAzione(), i, 
						eventi[i].getTipoControllo(), eventi[i].var1, Integer.parseInt(eventi[1].var2));
			}
		}
				
		return START_STICKY;
	}
	
    @Override
    public void onDestroy() {
        // TODO rilascia i listener e chiudi tutto
    	super.onDestroy();
    }
	
    //Invece di connettere evento e azione, sono separati e c'e una variabile che rappresenta il tipo di azione
    //poi le azioni sono gestite nel receiver
	
	
    
    //Eventuali altre condizioni che necessiteranno le azioni, vengono aggiunte nel database e verranno gestite dal receiver
    //(per esempio eventuali messaggi da inviare, o numeri da bloccare)
	public void controlloOrario (int tipoAzione, int numEvento, int tipoControllo, final int ora, final int minuti, 
							 final int giorno, final int mese, final int anno) { //1 ora 2 ora e giorno 3 mese 4 anno

		Intent intent = new Intent(this, EventReceiver.class);
		intent.putExtra("tipoAzione", tipoAzione);
		intent.putExtra("numEvento", numEvento);
		intent.putExtra("tipoControllo", tipoControllo);
		intent.putExtra("ora", ora);
		intent.putExtra("minuti", minuti);
		intent.putExtra("giorno", giorno);
		intent.putExtra("mese", mese);
		intent.putExtra("anno", anno);
		PendingIntent sender = PendingIntent.getBroadcast(this, RICHIESTA+numEvento, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		//AlarmManager service
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 61000, sender);
	}
	
	public void controlloBatteria (int tipoAzione, int numEvento, int tipoControllo, int livello) {
		Intent intent = new Intent(this, EventReceiver.class);
		intent.putExtra("tipoAzione", tipoAzione);
		intent.putExtra("numEvento", numEvento);
		intent.putExtra("tipoControllo", tipoControllo);
		intent.putExtra("livello", livello);
		PendingIntent sender = PendingIntent.getBroadcast(this, RICHIESTA+numEvento, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Alarm manager
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 61000, sender);
	}
	
	public void controlloGPS (int tipoAzione, int numEvento, int tipoControllo, double lat, double lon) {
		Intent intent = new Intent(this, EventReceiver.class);
		intent.putExtra("tipoAzione", tipoAzione);
		intent.putExtra("numEvento", numEvento);
		intent.putExtra("tipoControllo", tipoControllo);
		intent.putExtra("lat", lat);
		intent.putExtra("lon", lon);
		PendingIntent sender = PendingIntent.getBroadcast(this, RICHIESTA+numEvento, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Alarm manager
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 61000, sender);
	}
	
	public void controlloWeather (int tipoAzione, int numEvento, int tipoControllo, String condizione) {
		Intent intent = new Intent(this, EventReceiver.class);
		intent.putExtra("tipoAzione", tipoAzione);
		intent.putExtra("numEvento", numEvento);
		intent.putExtra("tipoControllo", tipoControllo);
		intent.putExtra("condizione", condizione);
		//nuvoloso piove sereno
		PendingIntent sender = PendingIntent.getBroadcast(this, RICHIESTA+numEvento, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Alarm manager
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 61000, sender);
	}
	
	public void controlloTemperatura (int tipoAzione, int numEvento, int tipoControllo, String condizione, int temp) {
		Intent intent = new Intent(this, EventReceiver.class);
		intent.putExtra("tipoAzione", tipoAzione);
		intent.putExtra("numEvento", numEvento);
		intent.putExtra("tipoControllo", tipoControllo);
		intent.putExtra("condizione", condizione);
		//maggiore minore uguale
		intent.putExtra("temp", temp);
		PendingIntent sender = PendingIntent.getBroadcast(this, RICHIESTA+numEvento, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Alarm manager
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 61000, sender);
	}
}
