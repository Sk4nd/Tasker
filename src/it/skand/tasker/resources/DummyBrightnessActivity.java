package it.skand.tasker.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("HandlerLeak")
public class DummyBrightnessActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Window window = getWindow();

    	//I touch event "trapassano" l'activity
    	window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    	
        Intent brightnessIntent = this.getIntent();
        float brightness = brightnessIntent.getFloatExtra("brightness value", 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = brightness;
        getWindow().setAttributes(lp);
    }

}