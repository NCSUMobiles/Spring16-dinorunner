package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import java.io.IOException;

import static com.dino.ncsu.dinorunner.FileOperations.bytes2Object;

public class RunningActivity extends Activity implements SensorEventListener{

    //Private variables in this class
    private Dinosaur dino;
    private Track track;
    private int lapsDone;
    private int totalLaps;
    private int distanceTraveled;
    private int totalDistance;
    private Player player;
    private float speed;
    private boolean activityRunning = true;

    //Pedometer Data

    private SensorManager mSensorManager;

    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_running);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();

        //load pedometer sensors
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        //Laps, distance traveled data
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

    //When backbutton is pressed
    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

    //When steps are detected
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) { //Sets total steps to this when step counter is changed
            distanceTraveled = value;
        }
        else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) { //Increases step counter by 1 when step is taken
            distanceTraveled += 1;
        }
    }

    //Unpause
    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
    }

    //Stopped
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    //Pause
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    //Dunno what this does
    @Override
    public void onAccuracyChanged (Sensor sensor, int accuracy) {
        //Nothing yet
    }

    //Returns player speed
    public float getPlayerSpeed() {

        return this.speed;
    }

    //Returns distance traveled
    public int getDistanceTraveled() {
        return this.distanceTraveled;
    }
}
