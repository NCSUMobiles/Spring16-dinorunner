package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by yao on 3/14/16.
 */
public class MapViewActivity extends Activity {

    private float current_speed; //Current player speed

    Context context;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(new MapView(this));
        //Acquire reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        //Defines a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                location.getLatitude();
                Toast.makeText(context, "Current speed:" + location.getSpeed(), Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

                //DO SOMETHING
            }

            public void onProviderEnabled(String provider) {

                //DO SOMETHING
            }

            public void onProviderDisabled(String provider) {

                //DO SOMETHING
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public float getSpeed() {
        return this.current_speed;
    }
}
