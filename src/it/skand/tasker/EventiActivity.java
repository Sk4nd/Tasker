package it.skand.tasker;
//TODO interfaccia

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

public class EventiActivity extends Activity {
	
	private MyOpenHelper oh;
	private DataSource db;
	
	RadioButton evento1;
	RadioButton evento2;
	RadioButton evento3;
	RadioButton evento4;
	RadioButton evento5;
	RadioButton evento6;
	RadioButton evento7;
	RadioButton evento8;
	RadioButton evento9;
	RadioButton evento10;
	RadioButton evento11;
	
	Button elimina;
	Button next;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventi);
		
		oh=new MyOpenHelper(this);
    	db=new DataSource(oh);
    	
    	evento1=(RadioButton)findViewById(R.id.evento1);
    	evento2=(RadioButton)findViewById(R.id.evento2);
    	evento3=(RadioButton)findViewById(R.id.evento3);
    	evento4=(RadioButton)findViewById(R.id.evento4);
    	evento5=(RadioButton)findViewById(R.id.evento5);
    	evento6=(RadioButton)findViewById(R.id.evento6);
    	evento7=(RadioButton)findViewById(R.id.evento7);
    	evento8=(RadioButton)findViewById(R.id.evento8);
    	evento9=(RadioButton)findViewById(R.id.evento9);
    	evento10=(RadioButton)findViewById(R.id.evento10);
    	evento11=(RadioButton)findViewById(R.id.evento11);
    	
    	elimina=(Button)findViewById(R.id.elimina);
    	next=(Button)findViewById(R.id.next);
    	
    	Cursor cu=db.lista();
    	settaRadio(cu);
    	
    	//ONCLICK ELIMINA
    	elimina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (evento1.isChecked()) {
					String ids=evento1.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento2.isChecked()) {
					String ids=evento2.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento3.isChecked()) {
					String ids=evento3.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento4.isChecked()) {
					String ids=evento4.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento5.isChecked()) {
					String ids=evento5.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento6.isChecked()) {
					String ids=evento6.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento7.isChecked()) {
					String ids=evento7.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento8.isChecked()) {
					String ids=evento8.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento9.isChecked()) {
					String ids=evento9.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento10.isChecked()) {
					String ids=evento10.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
				if (evento11.isChecked()) {
					String ids=evento11.getText().toString();
					int id=Integer.parseInt(ids.substring(0, 1));
					db.eliminaEvento(id);
					Cursor ci=db.lista();
					settaRadio(ci);
				}
			}
		});
	}
	
	public void settaRadio (Cursor cu) {
		int i=1;
		evento1.setVisibility(View.INVISIBLE);
		evento2.setVisibility(View.INVISIBLE);
		evento3.setVisibility(View.INVISIBLE);
		evento4.setVisibility(View.INVISIBLE);
		evento5.setVisibility(View.INVISIBLE);
		evento6.setVisibility(View.INVISIBLE);
		evento7.setVisibility(View.INVISIBLE);
		evento8.setVisibility(View.INVISIBLE);
		evento9.setVisibility(View.INVISIBLE);
		evento10.setVisibility(View.INVISIBLE);
		evento11.setVisibility(View.INVISIBLE);
    	
    	while (cu.moveToNext()) {
    		if (i==12)
    			break;
    		else if (i==1) {
    			evento1.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento1.setVisibility(View.VISIBLE);
    		}
    		else if (i==2) {
    			evento2.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento2.setVisibility(View.VISIBLE);
    		}
    		else if (i==3) {
    			evento3.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento3.setVisibility(View.VISIBLE);
    		}
    		else if (i==4) {
    			evento4.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento4.setVisibility(View.VISIBLE);
    		}
    		else if (i==5) {
    			evento5.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento5.setVisibility(View.VISIBLE);
    		}
    		else if (i==6) {
    			evento6.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento6.setVisibility(View.VISIBLE);
    		}
    		else if (i==7) {
    			evento7.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento7.setVisibility(View.VISIBLE);
    		}
    		else if (i==8) {
    			evento8.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento8.setVisibility(View.VISIBLE);
    		}
    		else if (i==9) {
    			evento9.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento9.setVisibility(View.VISIBLE);
    		}
    		else if (i==10) {
    			evento10.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento10.setVisibility(View.VISIBLE);
    		}
    		else if (i==11) {
    			evento11.setText(cu.getInt(cu.getColumnIndex("id"))+" "+
    					cu.getInt(cu.getColumnIndex("tipocontrollo"))+" "+
    					cu.getInt(cu.getColumnIndex("tipoazione"))+" "+
    					cu.getString(cu.getColumnIndex("var1"))+" "+
    					cu.getString(cu.getColumnIndex("var2"))+" "+
    					cu.getString(cu.getColumnIndex("var3"))+" "+
    					cu.getString(cu.getColumnIndex("var4"))+" "+
    					cu.getString(cu.getColumnIndex("var5")));
    			evento11.setVisibility(View.VISIBLE);
    		}
    		i++;
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}