package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import static com.dino.ncsu.dinorunner.FileOperations.bytes2Object;

public class RunningActivity extends Activity {

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

        MapViewActivity map = new MapViewActivity();
        speed = map.getSpeed();

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
}
