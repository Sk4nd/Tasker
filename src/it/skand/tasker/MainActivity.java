package it.skand.tasker;
//TODO
//Activity iniziale che spiega il funzionamento dell'app

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	Context context;
	public int NUM_EVENTI;
	
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
	//TODO
	//imposta sveglia
	//blocca chiamate e messaggi
	private static final int NULL=0;
	
	//interfaccia
	private Button add;
	private Button clear;
	private Button list;
	
	//private RadioButton radio0;
	//private RadioButton radio1;
	//private RadioButton radio2;
	//private RadioButton radio3;
	//private RadioButton radio4;
	//private RadioButton radio10;
	//private RadioButton radio11;
	
	//private EditText campo1;
	//private EditText campo2;
	//private EditText campo3;
	//private EditText time;
	//private EditText data;
	private EditText toParse;
	
	
	//Database
	private MyOpenHelper oh;
	private DataSource db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context=this;
		NUM_EVENTI=0;
		
		//inizializzazione interfaccia
		add=(Button)findViewById(R.id.elimina);
		clear=(Button)findViewById(R.id.prev);
		list=(Button)findViewById(R.id.next);
		//radio0=(RadioButton)findViewById(R.id.evento6);
		//radio1=(RadioButton)findViewById(R.id.evento10);
		//radio2=(RadioButton)findViewById(R.id.evento11);
		//radio3=(RadioButton)findViewById(R.id.radio3);
		//radio4=(RadioButton)findViewById(R.id.radio4);
		//radio10=(RadioButton)findViewById(R.id.radio10);
		//radio11=(RadioButton)findViewById(R.id.radio11);
		//campo1=(EditText)findViewById(R.id.campo1);
		//campo2=(EditText)findViewById(R.id.campo2);
		//campo3=(EditText)findViewById(R.id.campo3);
		//time=(EditText)findViewById(R.id.time);
		//data=(EditText)findViewById(R.id.data);
		toParse=(EditText)findViewById(R.id.toParse);
		
		
		//inizializzazione db
		oh=new MyOpenHelper(this);
    	db=new DataSource(oh);
		
		//RADIO BUTTONS
		/*if(radio0.isChecked()) {
			clear();
			time.setText("orario");
		}
		else if(radio1.isChecked()) {
			clear();
			time.setText("orario");
			campo1.setText("giorno");
		}
		else if(radio2.isChecked()) {
			clear();
			data.setText("data");
		}
		else if(radio3.isChecked()) {
			clear();
			campo1.setText("livello batteria");
		}
		else if(radio4.isChecked()) {
			clear();
			campo1.setText("latitudine");
			campo2.setText("longitudine");
		}
		
		if (radio10.isChecked()) {
			campo3.setText("suoneria");
		}
		else if (radio11.isChecked()) {
			campo3.setText("luminosita");
		}
		
		
		//onclicklisteners radio buttons
		radio0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clear();
				time.setText("orario");
			}
		});
		radio1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clear();
				time.setText("orario");
				campo1.setText("giorno");
			}
		});
		radio2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clear();
				time.setText("orario");
				data.setText("data");
			}
		});
		radio3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clear();
				campo1.setText("livello batteria");
			}
		});
		radio4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clear();
				campo1.setText("latitudine");
				campo2.setText("longitudine");
			}
		});
		radio10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				campo3.setText("suoneria");
			}
		});
		radio11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				campo3.setText("luminosita");
			}
		});*/
		//FINE RADIO BUTTONS
		
		
		//STARTA IL SERVIZIO
		final Intent intent=new Intent(this, Servizio.class);
		startService(intent);
		
		
		//ONCLICKLISTENERS PULSANTI
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clear();
			}			
		});
		add.setOnClickListener(new OnClickListener() {
			TextList text;
			Parser parsed;
			@Override
			public void onClick(View v) {
				
				
				NUM_EVENTI++;
				
				//Prende il testo inserito e lo salva
				String daParsare=toParse.getText().toString();
				
				//Splitta sugli spazi e si salva una lista di stringhe
				text=new TextList(daParsare);
				text.inizialize();
				
				//Parsing delle stringhe
				parsed=new Parser(text.keyword, context);
				parsed.parse();
				
				//Inserisce evento nel db
				parsed.inserisciDb(context);
				
				
				
				//eventi da checkbox
				/*if (radio0.isChecked()) {
					tipoControllo=ORA;
					//prende l'ora dall'edittext
					String ore=time.getText().toString();
					boolean controllo=true;
					char[] a=ore.toCharArray();
					for (int i=0; i<a.length; i++) {
						if (controllo) {
							if (a[i]!=':')
								var1=var1+a[i];
							else
								controllo=false;
						}
						else
							var2=var2+a[i];
					}
				}
				else if (radio1.isChecked()) {
					tipoControllo=GIORNO;
					String ore=time.getText().toString();
					boolean controllo=true;
					char[] a=ore.toCharArray();
					for (int i=0; i<a.length; i++) {
						if (controllo) {
							if (a[i]!=':')
								var1=var1+a[i];
							else
								controllo=false;
						}
						else
							var2=var2+a[i];
					}
					var3=campo1.getText().toString();
				}
				else if (radio2.isChecked()) {
					tipoControllo=ANNO;
					String ore=time.getText().toString();
					boolean controllo=true;
					char[] a=ore.toCharArray();
					for (int i=0; i<a.length; i++) {
						if (controllo) {
							if (a[i]!=':')
								var1=var1+a[i];
							else
								controllo=false;
						}
						else
							var2=var2+a[i];
					}
					
					//prende la data dall'edittext
					String date=data.getText().toString();
					boolean controllo1=true;
					boolean controllo2=true;
					char[] d=date.toCharArray();
					for (int i=0; i<d.length; i++) {
						if (controllo1) {
							if (d[i]!='/')
								var3=var3+d[i];
							else
								controllo1=false;
						}
						else if (controllo2) {
							if (d[i]!='/')
								var4=var4+d[i];
							else
								controllo2=false;
						}
						else
							var5=var5+a[i];
					}
				}
				else if (radio3.isChecked()) {
					tipoControllo=BATTERIA;
					//livello batteria
					var1=campo1.getText().toString();
				}
				else if (radio4.isChecked()) {
					tipoControllo=GPS;
					//latitudine e longitudine
					var1=campo1.getText().toString();
					var2=campo2.getText().toString();
				}
				
				//azioni da checkbox
				if (radio10.isChecked()) {
					tipoAzione=Integer.parseInt(campo3.getText().toString());
				}
				else if (radio11.isChecked()) {
					tipoAzione=Integer.parseInt(campo3.getText().toString());
				}
				//controlli validita dati inseriti per le variabili
				//TODO
				//tutti i controlli su tutti i dati inseribili dall'utente
				if (var1=="")
					var1=""+NULL;
				if (var2=="")
					var2=""+NULL;
				if (var3=="")
					var3=""+NULL;
				if (var4=="")
					var4=""+NULL;
				if (var5=="")
					var5=""+NULL;
				if (testo=="")
					testo=""+NULL;
				if (numero=="")
					numero=""+NULL;
				
				//aggiorna il db
				db.inserisciEvento(NUM_EVENTI, tipoControllo, tipoAzione, var1, var2, var3, var4, var5, testo, numero);
				
				//riazzera le variabili
				var1="";
				var2="";
				var3="";
				var4="";
				var5="";
				
				//restarta il servizio per farlo aggiornare con i nuovi eventi
				stopService(intent);
				startService(intent);*/
			}			
		});
		
		
		
		//crea una nuova activity per list che mostri la lista degli eventi
		list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EventiActivity.class);
				startActivity(intent);
			}			
		});
		//FINE PULSANTI
		
		
		
		//db.inserisciEvento(0, ORA, NOTIFICA, 11+"", 42+"", ""+NULL, ""+NULL, ""+NULL, "trololol!! ASD", "3491522432");
		//int a;//cosi so dove faccio le prove
		
	}
	
	public void clear() {
		//campo1.setText("");
		//campo2.setText("");
		//campo3.setText("");
		//time.setText("");
		//data.setText("");
		toParse.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
