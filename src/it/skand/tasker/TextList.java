package it.skand.tasker;

import java.util.LinkedList;
import java.util.List;

public class TextList {
	
	List<String> keyword;
	String text;
	
	public TextList (String testo) {
		text=testo;
		keyword= new LinkedList<String>();
	}
	
	public void initialize () {
		String[] temp=text.split("\\s+");
		
		for (int i=0; i<temp.length; i++) {
			keyword.add(temp[i]);
		}
	}
	
	public String pop () {
		String result;
		try {
			result=keyword.remove(0);
			return result;
		}
		catch (IndexOutOfBoundsException e) {
			result="!";		//errore (simbolo terminale) cambialo
			return result;
		}
	}
	
	public void insert (String parola) {
		keyword.add(parola);
	}
	
}
