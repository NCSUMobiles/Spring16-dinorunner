package com.dino.ncsu.dinorunner.Pedometer;

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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.DrawSprites;
import com.dino.ncsu.dinorunner.Activity.LootActivity;
import com.dino.ncsu.dinorunner.MainActivity;
import com.dino.ncsu.dinorunner.Managers.RunManager;
import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.Objects.Track;
import com.dino.ncsu.dinorunner.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class holds the necessary functionality for the RunningActivity
 * <p/>
 * Keeps track of the progress running by the runner to see if the monster
 * is catching up to them.  Moves forward to the victory screen when the run is funished.
 * <p/>
 * Moves back to the main screen if the user presses the back button.
 */
public class RunningActivity extends Activity implements Runnable {
    //Private variables in this class
    private int totalLaps;
    private int lapsDone;
//    private static final String TAG = "DinoTag";

    //Pedometer Stuff
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private Utils mUtils;
    private long startTime; //Starting Time

    //    private TextView mStepValueView;
    private TextView mDistanceView;
    private TextView mDistanceLeftView;
    private TextView mSpeedView;
    private TextView mHealthView;


    private int mStepValue;
    private double SpeedValue;
    private double DistanceValue;
    private double DistanceLeftValue;
    private double TotalDistance;
    private double HealthValue;
    private double StepLength = 30.48; //Let's say everyone's feet is 1 foot or .3048 meters
    private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy

    //Dino Stuff
    private TextView mDinoDistanceView;
    private TextView mDinoSpeedView;
    private double DinoDistanceValue;
    private double DinoSpeedValue;

    /**
     * True, when service is running.
     */
    private boolean mIsRunning;

    //Surfaceview information
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    //Display information
    private DisplayMetrics display;
    private int width; //HTC ONE M8 = 1080
    private int height; //HTC ONE M8 = 1776

    //Thread information
    private Thread thread;
    private boolean locker = true;

    //All Equipment Logic here
    private ArrayList<Item> equipment = Player.getInstance().getListOfItems();

    //Equipment Location on Canvas
    private float EquipmentPos_X; //640
    private float EquipmentPos_Y; //940;

    //Equipment Frame Location on Canvas
    private float EquipmentFramePos_X; //600
    private float EquipmentFramePos_Y; //920

    //Map Details
    private float MapPos_X;
    private float MapPos_Y;

    //Scale for image size of different screens
    private float scale_width;
    private float scale_height;

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

    private Bitmap map;
    public static Typeface oldLondon;
    private Bitmap stat_frame;

    private DrawSprites drawSprites = null;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Screen information here
        //display = getWindowManager().getDefaultDisplay();
        display = this.getResources().getDisplayMetrics();
        //size = new Point();
        //display.getSize(size);
        width = display.widthPixels;
        height = display.heightPixels;


        //Bitmap for frame: Character
        scale_width = width / 1080;
        scale_height = height / 1776;

        character_frame = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_character);
//        Log.d("item1", "" + equipment.get(0).getImageId());
//        Log.d("head", "" + R.mipmap.default_head);
//        Log.d("item2", "" + equipment.get(1).getImageId());
//        Log.d("chest", "" + R.mipmap.default_chest);
//        Log.d("item3", "" + equipment.get(2).getImageId());
//        Log.d("shirt", "" + R.mipmap.default_shirt);
//        Log.d("item4", "" + equipment.get(3).getImageId());
//        Log.d("pants", "" + R.mipmap.default_pants);
//        Log.d("item5", "" + equipment.get(4).getImageId());
//        Log.d("shoes", "" + R.mipmap.default_shoes);

        equipped_head = BitmapFactory.decodeResource(getResources(), equipment.get(0).getImageId());
        equipped_chest = BitmapFactory.decodeResource(getResources(), equipment.get(1).getImageId());
        equipped_pants = BitmapFactory.decodeResource(getResources(), equipment.get(2).getImageId());
        equipped_shoes = BitmapFactory.decodeResource(getResources(), equipment.get(3).getImageId());

        //Bitmap for frame: Track
        map = BitmapFactory.decodeResource(getResources(), Track.getInstance().getTrackImageId());

        //Bitmap for statistics frame
        stat_frame = BitmapFactory.decodeResource(getResources(), R.mipmap.stat_frame);

        if (character_frame != null && Math.round(character_frame.getWidth() * scale_width) != 0 && Math.round(character_frame.getHeight() * scale_height) != 0)
            character_frame = Bitmap.createScaledBitmap(character_frame, Math.round(character_frame.getWidth() * scale_width), Math.round(character_frame.getHeight() * scale_height), false);

        if (equipped_head != null && Math.round(equipped_head.getWidth() * scale_width) != 0 && Math.round(equipped_head.getHeight() * scale_height) != 0)
            equipped_head = Bitmap.createScaledBitmap(equipped_head, Math.round(equipped_head.getWidth() * scale_width), Math.round(equipped_head.getHeight() * scale_height), false);

        if (equipped_chest != null && Math.round(equipped_chest.getWidth() * scale_width) != 0 && Math.round(equipped_chest.getHeight() * scale_height) != 0)
            equipped_chest = Bitmap.createScaledBitmap(equipped_chest, Math.round(equipped_chest.getWidth() * scale_width), Math.round(equipped_chest.getHeight() * scale_height), false);

        if (equipped_pants != null && Math.round(equipped_pants.getWidth() * scale_width) != 0 && Math.round(equipped_pants.getHeight() * scale_height) != 0)
            equipped_pants = Bitmap.createScaledBitmap(equipped_pants, Math.round(equipped_pants.getWidth() * scale_width), Math.round(equipped_pants.getHeight() * scale_height), false);

        if (equipped_shoes != null && Math.round(equipped_shoes.getWidth() * scale_width) != 0 && Math.round(equipped_shoes.getHeight() * scale_height) != 0)
            equipped_shoes = Bitmap.createScaledBitmap(equipped_shoes, Math.round(equipped_shoes.getWidth() * scale_width), Math.round(equipped_shoes.getHeight() * scale_height), false);

        if (map != null && Math.round(map.getWidth() * scale_width) != 0 && Math.round(map.getHeight() * scale_height) != 0)
            map = Bitmap.createScaledBitmap(map, Math.round(map.getWidth() * scale_width), Math.round(map.getHeight() * scale_height), false);

        if (stat_frame != null && Math.round(stat_frame.getWidth() * scale_width) != 0 && Math.round(stat_frame.getHeight() * scale_height) != 0)
            stat_frame = Bitmap.createScaledBitmap(stat_frame, Math.round(stat_frame.getWidth() * scale_width), Math.round(stat_frame.getHeight() * scale_height), false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_activity);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();

        //load meta data here.
//        Bundle infoBundle = getIntent().getExtras();
        //Steps stuff
        mStepValue = 0;
        startTime = mUtils.currentTimeInMillis();

        mUtils = Utils.getInstance();

        //Laps, distance traveled data
        clearProgress();

        //mStepValueView = (TextView) findViewById(R.id.stepView);
        mDistanceView = (TextView) findViewById(R.id.distanceView);
        mDistanceLeftView = (TextView) findViewById(R.id.distanceLeftView);
        mSpeedView = (TextView) findViewById(R.id.speedView);
        mHealthView = (TextView) findViewById(R.id.healthView);
        //Dino Stuff
        mDinoDistanceView = (TextView) findViewById(R.id.dinoDistanceView);
        mDinoSpeedView = (TextView) findViewById(R.id.dinoSpeedView);


        //Initialize player stats
        //Player.getInstance().setListOfItems(Inventory.getInstance().getEquippableItems());

        //Old London Text Style
        oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");

        mDinoDistanceView.setTypeface(oldLondon);
        mSpeedView.setTypeface(oldLondon);
        mHealthView.setTypeface(oldLondon);
        mDinoSpeedView.setTypeface(oldLondon);
        mDistanceView.setTypeface(oldLondon);
        mDistanceLeftView.setTypeface(oldLondon);

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
            //Log.d("test", "We Tested Step Service");
            startStepService();
            bindStepService();
        } else if (mIsRunning) {
            bindStepService();
        }

        mPedometerSettings.clearServiceRunning();

        resume();
        //Log.d(TAG, "onResume() called");
    }

    private void resume() {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onPause() {
        //Log.d("test", "WE PAUSED");
        if (mIsRunning) {
            //unbindStepService();
        }
        if (mQuitting) {
            mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
        } else {
            mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
        }

        super.onPause();
        //pause();
        //Log.d(TAG, "onPause() called");
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
        Log.d("servicetag","unbinded service");
    }

    private void stopStepService() {
        if (mService != null) {
            stopService(new Intent(RunningActivity.this,
                    StepService.class));

            Log.d("servicetag","stopped service");
        }
        mIsRunning = false;
    }

    private void resetValues(boolean updateDisplay) {
        if (mService != null && mIsRunning) {
            mService.resetValues();
        } else {
            //mStepValueView.setText("0");
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
                    Player.getInstance().setStepsTraveled(mStepValue);
                    DistanceValue = getmDistanceValue();
                    Player.getInstance().setDistance(DistanceValue);
                    DistanceLeftValue = getDistanceLeftValue();
                    SpeedValue = getSpeed();
                    Player.getInstance().setAvgSpeed(SpeedValue);
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
        return ((getStepsTraveled() == 0) ? 0.00 : (StepLength / deltaTime) * 100);
    }

    //Game logic + Draw Logic
    public void run() {
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            //Game logic
            checkPlayerDead();
            RunManager.getInstance().checkStunMonster();
            RunManager.getInstance().updateDistance();
            RunManager.getInstance().checkDistance();

            //UI updates
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DecimalFormat df = new DecimalFormat("#.#");

                    //Dino Stuff
                    DinoDistanceValue = RunManager.getInstance().getDistanceFromPlayer();
                    DinoSpeedValue = Dinosaur.getInstance().getSpeed();
                    HealthValue = Player.getInstance().getHealth();

                    mDinoDistanceView.setText("Distance: " + df.format(DinoDistanceValue) + " Meters");
                    mSpeedView.setText("Speed: " + df.format(SpeedValue) + " m/s");
                    mHealthView.setText("HP: " + df.format(HealthValue));
                    mDinoSpeedView.setText("Chasing: " + df.format(DinoSpeedValue) + " m/s");

                    //mStepValueView.setText("Steps: " + Integer.toString(mStepValue) + " Steps");
                    mDistanceView.setText("Distance: " + df.format(DistanceValue) + " Meters");
                    mDistanceLeftView.setText("Remaining: " + df.format(DistanceLeftValue) + " Meters");


                    //Logic checks for when Dino is on Player
                    if (DistanceValue <= 0 && (Player.getInstance().getDistance() > Dinosaur.getInstance().getHeadStart())) {
                        mDistanceView.setText("UNDER ATTACK!");
                    }

                    if (DinoSpeedValue <= 0 && (Player.getInstance().getDistance() > Dinosaur.getInstance().getHeadStart())) {
                        if (Dinosaur.getInstance().getStunned()) {
                            mDinoSpeedView.setText("STUNNED");
                        } else {
                            mDinoSpeedView.setText("UNDER ATTACK!");
                        }
                    } else if (Player.getInstance().getDistance() <= Dinosaur.getInstance().getHeadStart()) {
                        mDinoSpeedView.setText("Waiting to Chase: " + df.format(Dinosaur.getInstance().getHeadStart() - Player.getInstance().getDistance()) + "m left");
                    }
                }
            });

            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                draw(canvas);

                // End of painting to canvas. system will paint with this canvas,to the surface.
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void checkPlayerDead() {
        if (Player.getInstance().getHealth() <= 0) {
            Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
            dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.thread.interrupt();
            finish();
            startActivity(dataIntent);
        }
    }


    private void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(getResources().getColor(android.R.color.darker_gray));
            drawEquipment(canvas);
            drawMap(canvas);

            if (drawSprites == null) {
                drawSprites = new DrawSprites(canvas, getResources());
            }
            drawSprites.draw();
        }
    }

    //This method draws all the equipment
    public void drawEquipment(Canvas canvas) {
        //Positions
        EquipmentFramePos_X = 700 * scale_width;
        EquipmentFramePos_Y = 910 * scale_height;

        EquipmentPos_X = 740 * scale_width;
        EquipmentPos_Y = 930 * scale_height;

        equipped_head_POS_Y = equipped_head.getHeight();
        equipped_chest_POS_Y = equipped_head.getHeight();
        equipped_pants_POS_Y = equipped_chest.getHeight() + equipped_head.getHeight();
        equipped_shoes_POS_Y = equipped_pants.getHeight() + equipped_chest.getHeight() + equipped_head.getHeight();

        //Draws equipment screen
        canvas.drawBitmap(character_frame, EquipmentFramePos_X, EquipmentFramePos_Y, paint);
        canvas.drawBitmap(equipped_head, EquipmentPos_X, EquipmentPos_Y, paint);
        canvas.drawBitmap(equipped_chest, EquipmentPos_X, EquipmentPos_Y + equipped_chest_POS_Y, paint);
        canvas.drawBitmap(equipped_pants, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_shoes, EquipmentPos_X, EquipmentPos_Y + equipped_shoes_POS_Y, paint);

        //Draws stats screen
        canvas.drawBitmap(stat_frame, 0, 910 * scale_height, paint);
    }

    //This method draws the map
    public void drawMap(Canvas canvas) {
        MapPos_X = 0;
        MapPos_Y = 0;
        canvas.drawBitmap(map, MapPos_X, MapPos_Y, paint);
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
                                clearProgress();
                                //Check items are consumed by user
                                Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
                                dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                thread.interrupt();
                                finish();
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

    public void clearProgress() {
        Bundle infoBundle = getIntent().getExtras();

        //Laps, distance traveled data
        lapsDone = 0;
        totalLaps = infoBundle.getInt("lapsPicked");
        TotalDistance = infoBundle.getInt("distancePicked") * totalLaps;
        Track.getInstance().setTotalDistance(TotalDistance);
        Player.getInstance().setHealth(Player.getInstance().getMaxHealth());
        Dinosaur.getInstance().setDistance(0);
        Player.getInstance().setDistance(0);
        //Log.d("HeadStart", "" + Dinosaur.getInstance().getHeadStart());
    }

    @Override
    public void onDestroy() {
        unbindStepService();
        stopStepService();
        super.onDestroy();
        //Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        if (x >= 0 && x <= 100 && y >= 100 && y <= 300) {
            this.thread.interrupt();
            finish();
            startActivity(new Intent(getApplicationContext(), LootActivity.class));

        }
        Log.d("Coordinates of Touch: ", "" + x + "," + y);
        return false;
    }
}
