package it.skand.tasker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="db_eventi";
	private static final int DATABASE_VERSION=19;
	
	
	public MyOpenHelper(Context c) {
		super(c,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String create="CREATE TABLE EVENTI(id INT, tipocontrollo INT, "+
				"tipoazione INT, var1 TEXT, var2 TEXT, var3 TEXT, var4 TEXT, var5 TEXT, " +
				"testoSMS TEXT, numSMS TEXT);";
		db.execSQL(create);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS EVENTI");
		onCreate(db);
	}
}