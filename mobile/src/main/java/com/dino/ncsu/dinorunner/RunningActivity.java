package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;

import static com.dino.ncsu.dinorunner.FileOperations.bytes2Object;

/**
 * This class holds the necessary functionality for the RunningActivity
 * <p>
 * Keeps track of the progress running by the runner to see if the monster
 * is catching up to them.  Moves forward to the victory screen when the run is funished.
 * <p>
 * Moves back to the main screen if the user presses the back button.
 */
public class RunningActivity extends Activity implements Runnable {
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
    private long startTime; //Starting Time
    private long totalTime; //total time ran

    private TextView mStepValueView;
    private TextView mDistanceView;
    private TextView mDistanceLeftView;
    private TextView mSpeedView;


    private int mStepValue;
    private double SpeedValue;
    private double DistanceValue;
    private double DistanceLeftValue;
    private double TotalDistance;
    private double StepLength = 30.48; //Let's say everyone's feet is 1 foot or .3048 meters
    private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy

    /**
     * True, when service is running.
     */
    private boolean mIsRunning;

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean locker = true;

    //All Equipment Logic here

    EquippedItems equipment = EquippedItems.getInstance();

    //Equipment Location on Canvas
    float EquipmentPos_X = 640;
    float EquipmentPos_Y = 940;

    //Equipment Frame Location on Canvas
    float EquipmentFramePos_X = 600;
    float EquipmentFramePos_Y = 920;

    private Paint paint = new Paint();

    private Bitmap character_frame;

    private Bitmap equipped_head;
    private Bitmap equipped_chest;
    private Bitmap equipped_pants;
    private Bitmap equipped_shoes;

    private float equipped_head_POS_Y;
    private float equipped_chest_POS_Y;
    private float equipped_pants_POS_Y;
    private float equipped_shoes_POS_Y;

    float default_head_POS_Y;
    float default_torso_POS_Y;
    float default_pants_POS_Y;
    float default_shoes_POS_Y;

    Bitmap default_head;
    Bitmap default_torso;
    Bitmap default_pants;
    Bitmap default_shoes;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        default_head = BitmapFactory.decodeResource(getResources(), R.mipmap.default_head);
        default_torso = BitmapFactory.decodeResource(getResources(), R.mipmap.default_chest);
        default_pants = BitmapFactory.decodeResource(getResources(), R.mipmap.default_pants);
        default_shoes = BitmapFactory.decodeResource(getResources(), R.mipmap.default_shoes);

        default_head_POS_Y = default_head.getHeight();
        default_torso_POS_Y = default_head.getHeight();
        default_pants_POS_Y = default_torso.getHeight() + default_head_POS_Y;
        default_shoes_POS_Y = default_head.getHeight() + default_pants_POS_Y + default_torso_POS_Y;

        //Bitmap for frame:
        character_frame = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_character);

        equipped_head = BitmapFactory.decodeResource(getResources(), equipment.getHelmet().getImageId());
        equipped_chest = BitmapFactory.decodeResource(getResources(), equipment.getChest().getImageId());
        equipped_pants = BitmapFactory.decodeResource(getResources(), equipment.getPants().getImageId());
        equipped_shoes = BitmapFactory.decodeResource(getResources(), equipment.getShoes().getImageId());


        equipped_head_POS_Y = equipped_head.getHeight();
        equipped_chest_POS_Y = equipped_head.getHeight();
        equipped_pants_POS_Y = equipped_chest.getHeight() + equipped_head.getHeight();
        equipped_shoes_POS_Y = equipped_pants.getHeight() + equipped_chest.getHeight() + equipped_head.getHeight();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_activity);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();
        //Steps stuff
        mStepValue = 0;
        startTime = mUtils.currentTimeInMillis();

        mUtils = Utils.getInstance();

        //Laps, distance traveled data
        lapsDone = 0;
        totalLaps = infoBundle.getInt("lapsPicked");
        TotalDistance = infoBundle.getInt("distancePicked") * totalLaps;

        mStepValueView = (TextView) findViewById(R.id.stepView);
        mDistanceView = (TextView) findViewById(R.id.distanceView);
        mDistanceLeftView = (TextView) findViewById(R.id.distanceLeftView);
        mSpeedView = (TextView) findViewById(R.id.speedView);

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


        thread = new Thread(this);
        thread.start();

        resetValues(true);
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
        } else if (mIsRunning) {
            bindStepService();
        }

        mPedometerSettings.clearServiceRunning();

        resume();
    }

    private void resume() {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;

        thread = new Thread(this);
        thread.start();
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
        pause();
    }

    private void pause() {
        //CLOSE LOCKER FOR run();
        locker = false;
        while (true) {
            try {
                //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
                thread.join();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    private StepService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d("test", "WE CONNECTED TO SERVICE");
            mService = ((StepService.StepBinder) service).getService();

            mService.registerCallback(mCallback);
            mService.reloadSettings();
        }

        @Override
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
        } else {
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
    private static final int MENU_QUIT = 9;

    private static final int MENU_PAUSE = 1;
    private static final int MENU_RESUME = 2;

    // TODO: unite all into 1 type of message
    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            Message message = new Message();
            message.what = STEPS_MSG;
            message.arg1 = value;
            message.arg2 = 0;
            mHandler.sendMessage(message);
        }
    };

    private static final int STEPS_MSG = 1;
    private static final int DISTANCE_MSG = 2;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STEPS_MSG:
                    mStepValue = msg.arg1;
                    DecimalFormat df = new DecimalFormat("#.##");
                    DistanceValue = getmDistanceValue();
                    DistanceLeftValue = getDistanceLeftValue();
                    SpeedValue = getSpeed();

                    mStepValueView.setText("Steps: " + Integer.toString(mStepValue) + " Steps");
                    mDistanceView.setText("Distance: " + df.format(DistanceValue) + " Meters");
                    mDistanceLeftView.setText("Remaining: " + df.format(DistanceLeftValue) + " Meters");
                    mSpeedView.setText("Average Speed: " + df.format(SpeedValue) + " m/s");
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };


    /**
     * Returns steps traveled
     *
     * @return
     */
    public int getStepsTraveled() {
        return mStepValue;
    }

    public double getmDistanceValue() {
        return mStepValue * StepLength / 100;
//        return stepsTraveled * Player.getInstance().getmStepLength() / 12;
    }

    public double getDistanceLeftValue() {
        return TotalDistance - getmDistanceValue();
    }

    public double getSpeed() {
        long lastTime = System.currentTimeMillis();
        long deltaTime = lastTime - startTime;
        startTime = lastTime;
        Log.d("CurrentSpeed", "" + (StepLength / deltaTime) * 100);
        return ((getStepsTraveled() == 0) ? 0.00 : (StepLength / deltaTime) * 100);
    }


    public void run() {
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            draw(canvas);

            // End of painting to canvas. system will paint with this canvas,to the surface.
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void draw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(android.R.color.darker_gray));
        drawEquipment(canvas);
    }

    //This method draws all the equipment
    public void drawEquipment(Canvas canvas) {
        //Draws equipment screen
        canvas.drawBitmap(character_frame, EquipmentFramePos_X, EquipmentFramePos_Y, paint);
        canvas.drawBitmap(equipped_head, EquipmentPos_X, EquipmentPos_Y, paint);
        canvas.drawBitmap(equipped_chest, EquipmentPos_X, EquipmentPos_Y + equipped_chest_POS_Y, paint);
        canvas.drawBitmap(equipped_pants, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_shoes, EquipmentPos_X, EquipmentPos_Y + equipped_shoes_POS_Y, paint);
    }

    /**
     * Goes back to the Main Menu when the back button is pressed.
     * This will occur only if the user accepts the prompt to exit to the main menu.
     */
    @Override
    public void onBackPressed() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(RunningActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle("Warning!")
                        .setMessage("Are you sure you want to exit?\nYou will lose your current progress.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Only if the user accepts the prompt to exit to the main menu
                                //Check items are consumed by user
                                Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
                                dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dataIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing for right now
                            }
                        })
                        .show();
            }
        });
    }
}
