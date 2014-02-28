package it.skand.tasker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	private MyOpenHelper oh;
	
	public DataSource(MyOpenHelper o) {
		oh=o;
	}
	
	public void inserisciEvento(int id, int tipoControllo, int tipoAzione, String var1, String var2, String var3, String var4, String var5, String text, String numero) {
		SQLiteDatabase db=oh.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("id", id);
		values.put("tipocontrollo", tipoControllo);
		values.put("tipoazione", tipoAzione);
		values.put("var1", var1);
		values.put("var2", var2);
		values.put("var3", var3);
		values.put("var4", var4);
		values.put("var5", var5);
		values.put("testoSMS", text);
		values.put("numSMS", numero);
		db.insert("EVENTI", null, values);
	}
	
	public String cercaNumero(int i) {
		SQLiteDatabase db=oh.getReadableDatabase();
		Cursor cu=db.query("EVENTI", new String[]{"numSMS"}, "id="+i, null, null, null, null);
		while (cu.moveToNext()) {
			return cu.getString(cu.getColumnIndex("numSMS"));
		}
		return null;
	}
	
	public String cercaTesto(int i) {
		SQLiteDatabase db=oh.getReadableDatabase();
		Cursor cu=db.query("EVENTI", new String[]{"testoSMS"}, "id="+i, null, null, null, null);
		while (cu.moveToNext()) {
			return cu.getString(cu.getColumnIndex("testoSMS"));
		}
		return null;
	}
	
	public void eliminaEvento(int id) {
		SQLiteDatabase db=oh.getReadableDatabase();
		db.delete("EVENTI", "id="+id, null);
	}
	
	public int contaEventi() {
		SQLiteDatabase db=oh.getReadableDatabase();
		Cursor cu=db.query("EVENTI", null, null, null, null, null, null);
		int result=cu.getCount();
		cu.close();
		return result;
	}
	
	public int trovaSpazio() {
		SQLiteDatabase db=oh.getReadableDatabase();
		Cursor cu=db.query("EVENTI", new String[]{"id"}, null, null, null, null, null);
		int result=0;
		Boolean flag=false;
		for (result=0; result<200; result++) {
			flag=true;
			while (cu.moveToNext()) {
				if (cu.getInt(cu.getColumnIndex("id"))==result)
					flag=false;
			}
			if (flag)
				return result;
		}
		return 201;
	}
	
	public Cursor lista() {
		SQLiteDatabase db=oh.getReadableDatabase();
		Cursor cu;
		cu=(db.query("EVENTI", null, null, null, null, null, null, null));
		return cu;
	}
}
