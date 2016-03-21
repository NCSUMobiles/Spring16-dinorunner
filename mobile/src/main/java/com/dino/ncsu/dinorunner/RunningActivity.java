package com.dino.ncsu.dinorunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RunningActivity extends AppCompatActivity {

    //Private variables in this class
    private byte[] dino;
    private byte[] map;
    private int lapsDone;
    private int totalLaps;
    private double distanceTraveled;
    private int totalDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MapView(this));

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();
        dino = infoBundle.getByteArray("dinoPicked");
        map = infoBundle.getByteArray("mapPicked");
        lapsDone = 0;
        totalLaps = infoBundle.getInt("lapsPicked");
        distanceTraveled = 0;
        totalDistance = infoBundle.getInt("distancePicked") * totalLaps;
    }
    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }
}
