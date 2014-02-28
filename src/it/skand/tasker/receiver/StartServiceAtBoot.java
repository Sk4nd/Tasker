package it.skand.tasker.receiver;

import it.skand.tasker.Servizio;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//Receiver per startare il servizio allo startup del cellulare
public class StartServiceAtBoot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Intent serviceIntent=new Intent(context, Servizio.class);
			context.startService(serviceIntent);
		}
	}

}
