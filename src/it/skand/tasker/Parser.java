package it.skand.tasker;

//CAMBIA NOME AI CAMPI (DATE, TIME, RINGTONE, ECC) E AGGIUNGI IL ";"

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

public class Parser {
	
	List<String> keyword;
	List<String> tipo;
	List<String> controllo;
	List<String> azione;
	
	List<Integer> tipoControllo;
	List<Integer> tipoAzione;
	
	//Database
	private MyOpenHelper oh;
	private DataSource db;
	
	Context c;
	
	public Parser (List<String> keyword, Context c) {
		this.keyword=keyword;
		tipo=new LinkedList<String>();
		controllo=new LinkedList<String>();
		azione=new LinkedList<String>();
		tipoControllo=new LinkedList<Integer>();
		tipoAzione=new LinkedList<Integer>();
		this.c=c;
	}

	public void parse () {
		
		//TODO
		//SU AZIONE METTI, NEL CASO SIANO MESSAGGI O ROBE SIMILI, UNA VARIABILE CHE PRENDA IL VALORE DEL MESSAGGIO
		//TODO
		//CONTROLLI MULTIPLI (FLAG SU ERRORI, POI CATCHATE DA INSERTDB)
		
		boolean on=false;
		boolean iff=false;
		boolean doo=false;
		
		for (int i=0; i<keyword.size(); i++) {
			
			String a=keyword.get(i);
		
			if (a.equalsIgnoreCase("on")) {
				on=true;
			}
			else if (a.equalsIgnoreCase("if")) {
				iff=true;
				on=false;
				doo=false;
			}
			else if (a.equalsIgnoreCase("do")) {
				doo=true;
				iff=false;
				on=false;				
			}
			
			if (on) {
				if (a.equalsIgnoreCase("ora") || a.equalsIgnoreCase("giorno") || 
						a.equalsIgnoreCase("mese") || a.equalsIgnoreCase("anno") || 
						a.equalsIgnoreCase("batteria") || a.equalsIgnoreCase("gps") || 
						a.equalsIgnoreCase("tempo") || a.equalsIgnoreCase("temperatura")) {
					tipo.add(a);
				}
			}
			
			else if (iff) {
				if (!(a.equalsIgnoreCase("if"))) {
					controllo.add(a);					
				}
			}
			
			else if (doo) {
				if (a.equalsIgnoreCase("normale") || a.equalsIgnoreCase("vibrazione") || 
						a.equalsIgnoreCase("silenzioso") || a.equalsIgnoreCase("luminosita_low") || 
						a.equalsIgnoreCase("luminosita_med") || a.equalsIgnoreCase("luminosita_hig") || 
						a.equalsIgnoreCase("send_sms") || a.equalsIgnoreCase("send_toast") ||
						a.equalsIgnoreCase("notifica")) {
					azione.add(a);
				}
			}
		}
	}
	
	public void inserisciDb (Context c) {
		
		oh=new MyOpenHelper(c);
    	db=new DataSource(oh);
    	
    	for (int i=0; i<tipo.size(); i++) {
    		if (tipo.get(i).equalsIgnoreCase("ora"))
    			tipoControllo.add(1);
    		else if (tipo.get(i).equalsIgnoreCase("giorno"))
				tipoControllo.add(2);
    		else if (tipo.get(i).equalsIgnoreCase("mese"))
				tipoControllo.add(3);
    		else if (tipo.get(i).equalsIgnoreCase("anno"))
				tipoControllo.add(4);
    		else if (tipo.get(i).equalsIgnoreCase("batteria"))
				tipoControllo.add(5);
    		else if (tipo.get(i).equalsIgnoreCase("gps"))
				tipoControllo.add(6);
    		else if (tipo.get(i).equalsIgnoreCase("tempo"))
				tipoControllo.add(7);
    		else if (tipo.get(i).equalsIgnoreCase("temperatura"))
				tipoControllo.add(8);
    	}
    	
    	for (int i=0; i<azione.size(); i++) {
    		if (azione.get(i).equalsIgnoreCase("normale"))
				tipoAzione.add(1);
			else if (azione.get(i).equalsIgnoreCase("vibrazione"))
				tipoAzione.add(2);
			else if (azione.get(i).equalsIgnoreCase("silenzioso"))
				tipoAzione.add(3);
			else if (azione.get(i).equalsIgnoreCase("luminosita_low"))
				tipoAzione.add(4);
			else if (azione.get(i).equalsIgnoreCase("luminosita_med"))
				tipoAzione.add(5);
			else if (azione.get(i).equalsIgnoreCase("luminosita_hig"))
				tipoAzione.add(6);
			else if (azione.get(i).equalsIgnoreCase("send_sms"))
				tipoAzione.add(7);
			else if (azione.get(i).equalsIgnoreCase("send_toast"))
				tipoAzione.add(8);
			else if (azione.get(i).equalsIgnoreCase("notifica"))
				tipoAzione.add(9);
    	}

		if (tipoControllo.size()==1) {
			String var1 = null;
			String var2 = null;
			String var3 = null;
			String var4 = null;
			String var5 = null;
			String var6 = null;
			String var7 = null;
			
			try {
				var1=controllo.get(0);
			}
			catch (IndexOutOfBoundsException e) {
				var1=""+0;
			}
			try {
				var2=controllo.get(1);
			}
			catch (IndexOutOfBoundsException e) {
				var2=""+0;
			}
			try {
				var3=controllo.get(2);
			}
			catch (IndexOutOfBoundsException e) {
				var3=""+0;
			}
			try {
				var4=controllo.get(3);
			}
			catch (IndexOutOfBoundsException e) {
				var4=""+0;
			}
			try {
				var5=controllo.get(4);
			}
			catch (IndexOutOfBoundsException e) {
				var5=""+0;
			}
			try {
				var6=controllo.get(5);
			}
			catch (IndexOutOfBoundsException e) {
				var6=""+0;
			}
			try {
				var7=controllo.get(6);
			}
			catch (IndexOutOfBoundsException e) {
				var7=""+0;
			}
			
			
			for (int i=0; i<tipoAzione.size(); i++) {
				db.inserisciEvento(db.trovaSpazio(), tipoControllo.get(0), tipoAzione.get(i), var1, var2, var3, 
						var4, var5, var6, var7);
			}
		}
		//TODO
		//Inserisci else quando c'è più di un controllo da fare
	}
	
}
