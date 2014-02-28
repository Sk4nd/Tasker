package it.skand.tasker;

public class Evento {
	int tipoControllo;
	int tipoAzione;
	String var1;
	String var2;
	String var3;
	String var4;
	String var5;
	
	public Evento(int tipoControllo, int tipoAzione, String var1, String var2, String var3, String var4, String var5) {
		this.tipoControllo=tipoControllo;
		this.tipoAzione=tipoAzione;
		this.var1=var1;
		this.var2=var2;
		this.var3=var3;
		this.var4=var4;
		this.var5=var5;
	}
	
	public int getTipoControllo() {
		return tipoControllo;
	}
	public int getTipoAzione() {
		return tipoAzione;
	}
	
	public void setTipoControllo(int tipoControllo) {
		this.tipoControllo=tipoControllo;
	}
	public void setTipoAzione(int tipoAzione) {
		this.tipoAzione=tipoAzione;
	}
}
