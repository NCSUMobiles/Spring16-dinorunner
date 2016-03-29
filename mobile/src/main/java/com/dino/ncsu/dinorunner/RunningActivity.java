package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import java.io.IOException;

import static com.dino.ncsu.dinorunner.FileOperations.bytes2Object;

public class RunningActivity extends Activity implements  IBaseGpsListener {

    //Private variables in this class
    private Dinosaur dino;
    private Track track;
    private int lapsDone;
    private int totalLaps;
    private double distanceTraveled;
    private int totalDistance;
    private Player player;
    private float speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MapView(this));

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();

        lapsDone = 0;
        totalLaps = infoBundle.getInt("lapsPicked");
        distanceTraveled = 0;
        totalDistance = infoBundle.getInt("distancePicked") * totalLaps;

        //Initialize player stats
        player = new Player();
        try {
            player.setListOfItems((EquippedItems) bytes2Object(infoBundle.getByteArray("itemsPicked")));
            dino = (Dinosaur) bytes2Object(infoBundle.getByteArray("dinoPicked"));
            track = (Track) bytes2Object(infoBundle.getByteArray("mapPicked"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Gps stuff goes here:
        //Acquire reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                0,
                this);

        this.updateSpeed(null);

    }
    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

    public float getPlayerSpeed() {

        return this.speed;
    }

    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    public void updateSpeed(Location location)
    {
        float nCurrentSpeed = 0;

        if( location!=null )
        {
            nCurrentSpeed = location.getSpeed();
            speed = nCurrentSpeed;
        }
    }

    public void onLocationChanged(Location location)
    {
        if (location != null)
        {
            Location myLocation = new Location(location);
            this.updateSpeed(myLocation);
        }
    }

    public void onProviderDisabled(String provider)
    {
        // TODO: do something one day?
    }

    public void onProviderEnabled(String provider)
    {
        // TODO: do something one day?
    }

    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO: do something one day?

    }

    public void onGpsStatusChanged(int event)
    {
        // TODO: do something one day?
    }
}
