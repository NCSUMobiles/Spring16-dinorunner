package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

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
    private Player player;
    private double speed;
    private boolean activityRunning = true;
    private int stepsTraveled; //Steps traveled total
    private int stepsLap; //Total amount of steps per lap

    //Pedometer Stuff
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private Utils mUtils;

    private TextView mStepValueView;
    private int mStepValue;
    private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy

    /**
     * True, when service is running.
     */
    private boolean mIsRunning;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();

        //Steps stuff
        mStepValue = 0;

        mUtils = Utils.getInstance();

        //Laps, distance traveled data
        lapsDone = 0;
        totalLaps = infoBundle.getInt("lapsPicked");

        mStepValueView     = (TextView) findViewById(R.id.stepView);


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

        setContentView(R.layout.activity_running);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mPedometerSettings = new PedometerSettings(mSettings);

        // Read from preferences if the service was running on the last onPause
        mIsRunning = mPedometerSettings.isServiceRunning();

        // Start the service if this is considered to be an application start (last onPause was long ago)
        if (!mIsRunning && mPedometerSettings.isNewStart()) {
            Log.d("test", "We Tested Step Service");
            startStepService();
            bindStepService();
        }
        else if (mIsRunning) {
            bindStepService();
        }

        mPedometerSettings.clearServiceRunning();




    }

    @Override
    protected void onPause() {
        Log.d("test", "WE PAUSED");
        if (mIsRunning) {
            unbindStepService();
        }
        if (mQuitting) {
            mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
        } else {
            mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
        }

        super.onPause();
    }
    private StepService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d("test", "WE CONNECTED TO SERVICE");
            mService = ((StepService.StepBinder)service).getService();

            mService.registerCallback(mCallback);
            mService.reloadSettings();
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private void startStepService() {
        Log.d("test", "WE STARTED STEP SERVICE");
        if (!mIsRunning) {
            mIsRunning = true;
            startService(new Intent(RunningActivity.this,
                    StepService.class));
        }
    }

    private void bindStepService() {
        bindService(new Intent(RunningActivity.this,
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }

    private void unbindStepService() {
        unbindService(mConnection);
    }

    private void stopStepService() {
        if (mService != null) {
            stopService(new Intent(RunningActivity.this,
                    StepService.class));
        }
        mIsRunning = false;
    }

    private void resetValues(boolean updateDisplay) {
        if (mService != null && mIsRunning) {
            mService.resetValues();
        }
        else {
            mStepValueView.setText("0");
            SharedPreferences state = getSharedPreferences("state", 0);
            SharedPreferences.Editor stateEditor = state.edit();
            if (updateDisplay) {
                stateEditor.putInt("steps", 0);
                stateEditor.commit();
            }
        }
    }

    private static final int MENU_SETTINGS = 8;
    private static final int MENU_QUIT     = 9;

    private static final int MENU_PAUSE = 1;
    private static final int MENU_RESUME = 2;

    // TODO: unite all into 1 type of message
    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
        }
    };

    private static final int STEPS_MSG = 1;

    private Handler mHandler = new Handler() {

        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case STEPS_MSG:
                    mStepValue = (int)msg.arg1;
                    mStepValueView.setText("" + mStepValue);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };


    /**
     * Returns steps traveled
     * @return
     */
    public int getStepsTraveled() {
        return this.stepsTraveled;
    }
}
