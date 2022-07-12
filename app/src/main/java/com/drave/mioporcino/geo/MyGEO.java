package com.drave.mioporcino.geo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


public class MyGEO {

    Context context;

    public MyGEO(Context _context) {
        context=_context;
    }

    public static double lastLatitude=0.0;
    public static double lastLongitude=0.0;
    public static long   lastGPSTimeMillis=0;   //IN MILLISECONDI!

    LocationManager locationManager;
    private static final int MIN_DIST=0;
    private static final int MIN_PERIOD=1000;

    private LocationListener locationListener = new LocationListener()
    {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            //logga("onStatusChanged, provider="+provider+" Stato="+ Ut.cstr(status));
        }
        @Override
        public void onProviderEnabled(String provider)
        {
            logga("Provider="+provider+" Enabled");
        }
        @Override
        public void onProviderDisabled(String provider)
        {
            logga("Provider="+provider+" Disabled");
        }

        @Override
        public void onLocationChanged(Location location)
        {
            memGeo(location);
        }
    };

    public boolean start(){

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            if (locationManager!=null){
                logga("OK start , get location Manager");

                if (permissionGPS()){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_PERIOD,MIN_DIST, locationListener, Looper.myLooper().getMainLooper());
                    memGeo(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                    logga("OK attivata richiesta di aggiornamento coordinate da GPS");
                }
                else {
                    logga("GPS permessi revocati");
                }

                /*if (permissionCOARSE()){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_PERIOD,MIN_DIST, locationListener, Looper.myLooper().getMainLooper());
                    memGeo(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
                    logga("OK attivata richiesta di aggiornamento coordinate da network");
                }
                else {
                    logga("NETWORK permessi revocati");
                }*/

                return true;
            }
        }
        catch (SecurityException exc){
            logga("ERRORE start:"+exc.getMessage());
            return false;
        }
        return true;
    }

    public void stop(){
        try {
            if (locationManager!=null)
                locationManager.removeUpdates(locationListener);
        }
        catch (Exception exc){
        }

    }

    private void logga(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    private void logerr(String moreInfo,Exception e) {
        logga(moreInfo + ". Err: "+ e.getMessage());
    }

    private void memGeo(Location location){
        try {
            if (location==null) return;

            lastLatitude= location.getLatitude();
            lastLongitude= location.getLongitude();
            lastGPSTimeMillis= location.getTime();

            //logga("Ultima acquisizione coordinate. Provider="+location.getProvider());
        }
        catch (Exception exc){
        }

    }

    private boolean permissionGPS(){
        return  (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
}
