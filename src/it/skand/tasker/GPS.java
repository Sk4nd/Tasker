package it.skand.tasker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
 
public class GPS extends Service implements LocationListener {
 
    private final Context mContext;
 
    //flag per lo stato GPS
    boolean isGPSEnabled = false;
 
    //Flag per lo stato network
    boolean isNetworkEnabled = false;
 
    //Flag per lo stato GPS
    boolean canGetLocation = false;
 
    Location location; // localita
    double latitude; // latitudine
    double longitude; // longitudine
 
    //distanza minima per cambiare updates
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 metri
 
    //tempo minimo
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minuto
 
    //Dichiarazione LocationManager
    protected LocationManager locationManager;
 
    public GPS(Context context) {
        this.mContext = context;
        getLocation();
    }
 
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
 
            //get stato GPS
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            //get stato network
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                //non si puo trovare la localita gps con nessun metodo
            } else {
                this.canGetLocation = true;
                //prima checka il network provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                //se c'e il gps trova lat e lon tramite quello
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
     
    //stoppa l'uso del gps
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPS.this);
        }       
    }
     
    //getter latitudine
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    //getter longitudine
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
     
    //funzione per vedere se network o gps sono enablati
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
 
    @Override
    public void onLocationChanged(Location location) {
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
}