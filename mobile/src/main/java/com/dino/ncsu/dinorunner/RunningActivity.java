package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

import static com.dino.ncsu.dinorunner.FileOperations.bytes2Object;

/**
 * This class holds the necessary functionality for the RunningActivity
 * <p>
 * Keeps track of the progress running by the runner to see if the monster
 * is catching up to them.  Moves forward to the victory screen when the run is funished.
 * <p>
 * Moves back to the main screen if the user presses the back button.
 */
public class RunningActivity extends Activity {
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

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_running);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();

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

    //Returns player speed
    public float getPlayerSpeed() {
        return this.speed;
    }

    //Returns distance traveled
    public int getDistanceTraveled() {
        return this.distanceTraveled;
    }
}
