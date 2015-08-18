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
	private static final int TIME=1;
	
	private static final int GIORNO=2;
	private static final int MESE=3;
	
	private static final int DATE=4;
	private static final int BATTERY=5;
	private static final int POSITION=6;
	private static final int WEATHER=7;
	private static final int TEMPERATURE=8;
	
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
	private EditText toParse;
	
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
		toParse=(EditText)findViewById(R.id.toParse);
		
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
				text.initialize();
				
				//Parsing delle stringhe
				parsed=new Parser(text.keyword, context);
				parsed.parse();
				
				//Inserisce evento nel db
				parsed.inserisciDb(context);
				
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
		
	}
	
	public void clear() {
		toParse.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
