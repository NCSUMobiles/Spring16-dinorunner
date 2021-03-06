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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dino.ncsu.dinorunner.Activity.LootActivity;
import com.dino.ncsu.dinorunner.DrawSprites;
import com.dino.ncsu.dinorunner.MainActivity;
import com.dino.ncsu.dinorunner.Managers.RunManager;
import com.dino.ncsu.dinorunner.Managers.SoundManager;
import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.Objects.Tile;
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
    private double tempStepLength; //Let's say everyone's feet is 1 foot or .3048 meters
    private double defaultStepLength;
    private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy
    private float totalBuff;

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
    private float width; //HTC ONE M8 = 1080
    private float height; //HTC ONE M8 = 1776

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
    private Bitmap equipped_shoulder;
    private Bitmap equipped_chest;
    private Bitmap equipped_shirt;
    private Bitmap equipped_glove;
    private Bitmap equipped_pants;
    private Bitmap equipped_shoes;
    private Bitmap equipped_cape;

    private float equipped_head_POS_Y;
    private float equipped_chest_POS_Y;
    private float equipped_pants_POS_Y;
    private float equipped_shoes_POS_Y;
    private int equipped_shirt_POS_Y;
    private Bitmap map;
    public static Typeface oldLondon;
    public static Typeface roman;

    private DrawSprites drawSprites = null;
    Bitmap bm_run_vertical;
    Bitmap bm_run_horizontal;
    Bitmap monster;
    Bitmap[] sprites = new Bitmap [3];

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    long fps;
    private int frameLengthInMilliseconds = 500;
    long timeThisFrame;
    private int frameWidth;
    private int frameHeight;
    private Rect frameToDraw = new Rect(
            0,
            0,
            frameWidth,
            frameHeight);
    RectF whereToDraw = new RectF(
            0,
            0,
            frameWidth,
            frameHeight);

    //Consumable Item Variables
    TextView mAmount1;
    TextView mAmount2;
    TextView mAmount3;
    TextView mAmount4;
    ImageView mConsume1;
    ImageView mConsume2;
    ImageView mConsume3;
    ImageView mConsume4;

    //Sounds:
    boolean isTicking;
    String prevTerrain;


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

        //Bitmaps for Sprites
        bm_run_vertical = BitmapFactory.decodeResource(getResources(), R.drawable.runman_vertical);
        bm_run_horizontal = BitmapFactory.decodeResource(getResources(), R.drawable.runman);
        monster = BitmapFactory.decodeResource(getResources(), Dinosaur.getInstance().getSpriteSheetId());
        sprites[0] = bm_run_vertical;
        sprites[1] = bm_run_horizontal;
        sprites[2] = monster;

        equipped_head = BitmapFactory.decodeResource(getResources(), equipment.get(0).getImageId());
        equipped_shoulder = BitmapFactory.decodeResource(getResources(), equipment.get(1).getImageId());
        equipped_chest = BitmapFactory.decodeResource(getResources(), equipment.get(2).getImageId());
        equipped_shirt = BitmapFactory.decodeResource(getResources(), equipment.get(3).getImageId());
        equipped_glove = BitmapFactory.decodeResource(getResources(), equipment.get(4).getImageId());
        equipped_pants = BitmapFactory.decodeResource(getResources(), equipment.get(5).getImageId());
        equipped_shoes = BitmapFactory.decodeResource(getResources(), equipment.get(6).getImageId());
        equipped_cape = BitmapFactory.decodeResource(getResources(), equipment.get(7).getImageId());

        //Bitmap for frame: Track
        map = BitmapFactory.decodeResource(getResources(), Track.getInstance().getTrackImageId());

        equipped_head = Bitmap.createScaledBitmap(equipped_head, Math.round(equipped_head.getWidth() * scale_width), Math.round(equipped_head.getHeight() * scale_height), false);
        equipped_shoulder = Bitmap.createScaledBitmap(equipped_shoulder, Math.round(equipped_shoulder.getWidth() * scale_width), Math.round(equipped_shoulder.getHeight() * scale_height), false);
        equipped_chest = Bitmap.createScaledBitmap(equipped_chest, Math.round(equipped_chest.getWidth() * scale_width), Math.round(equipped_chest.getHeight() * scale_height), false);
        equipped_shirt = Bitmap.createScaledBitmap(equipped_shirt, Math.round(equipped_shirt.getWidth() * scale_width), Math.round(equipped_shirt.getHeight() * scale_height), false);
        equipped_glove = Bitmap.createScaledBitmap(equipped_glove, Math.round(equipped_glove.getWidth() * scale_width), Math.round(equipped_glove.getHeight() * scale_height), false);
        equipped_pants = Bitmap.createScaledBitmap(equipped_pants, Math.round(equipped_pants.getWidth() * scale_width), Math.round(equipped_pants.getHeight() * scale_height), false);
        equipped_shoes = Bitmap.createScaledBitmap(equipped_shoes, Math.round(equipped_shoes.getWidth() * scale_width), Math.round(equipped_shoes.getHeight() * scale_height), false);
        equipped_cape = Bitmap.createScaledBitmap(equipped_cape, Math.round(equipped_cape.getWidth() * scale_width), Math.round(equipped_cape.getHeight() * scale_height), false);
        map = Bitmap.createScaledBitmap(map, Math.round(map.getWidth() * scale_width), Math.round(map.getHeight() * scale_height), false);

        frameWidth = Math.round(map.getWidth() / Track.getInstance().getFrameCount());
        frameHeight = Math.round(map.getHeight());
        frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
        whereToDraw = new RectF(0, 0, frameWidth, frameHeight);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_activity);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();

        mStepValue = 0;
        defaultStepLength = Player.getInstance().getTotalStepLength();
        tempStepLength = defaultStepLength;
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
        //items Stuff
        mAmount1 = (TextView) findViewById(R.id.amount_slot_1_view);
        mAmount2 = (TextView) findViewById(R.id.amount_slot_2_view);
        mAmount3 = (TextView) findViewById(R.id.amount_slot_3_view);
        mAmount4 = (TextView) findViewById(R.id.amount_slot_4_view);
        mConsume1 = (ImageView) findViewById(R.id.consume_slot1);
        mConsume2 = (ImageView) findViewById(R.id.consume_slot2);
        mConsume3 = (ImageView) findViewById(R.id.consume_slot3);
        mConsume4 = (ImageView) findViewById(R.id.consume_slot4);
        mConsume1.getLayoutParams().height = Math.round(200 * scale_height);
        mConsume1.getLayoutParams().width = Math.round(200 * scale_width);
        mConsume2.getLayoutParams().height = Math.round(200 * scale_height);
        mConsume2.getLayoutParams().width = Math.round(200 * scale_width);
        mConsume3.getLayoutParams().height = Math.round(200 * scale_height);
        mConsume3.getLayoutParams().width = Math.round(200 * scale_width);
        mConsume4.getLayoutParams().height = Math.round(200 * scale_height);
        mConsume4.getLayoutParams().width = Math.round(200 * scale_width);

        //Old London Text Style
        oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");
        roman = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/MorrisRomanBlack.ttf");

        mDinoDistanceView.setTypeface(oldLondon);
        mSpeedView.setTypeface(oldLondon);
        mHealthView.setTypeface(oldLondon);
        mDinoSpeedView.setTypeface(oldLondon);
        mDistanceView.setTypeface(oldLondon);
        mDistanceLeftView.setTypeface(oldLondon);
        mAmount1.setTypeface(roman);
        mAmount2.setTypeface(roman);
        mAmount3.setTypeface(roman);
        mAmount4.setTypeface(roman);
        mAmount1.setTextColor(Color.parseColor("#00FF00"));
        mAmount2.setTextColor(Color.parseColor("#00FF00"));
        mAmount3.setTextColor(Color.parseColor("#00FF00"));
        mAmount4.setTextColor(Color.parseColor("#00FF00"));
        //Sets imageviews for now

        setInventoryViews();

        //Sounds

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
        Log.d("servicetag", "unbinded service");
    }

    private void stopStepService() {
        if (mService != null) {
            stopService(new Intent(RunningActivity.this,
                    StepService.class));

            Log.d("servicetag", "stopped service");
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
                    DistanceValue += getmDistanceValue();
                    Player.getInstance().setDistance(DistanceValue);
                    DistanceLeftValue = getDistanceLeftValue();
                    SpeedValue = getSpeed()* totalBuff;
                    Player.getInstance().setAvgSpeed(SpeedValue);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    public int getStepsTraveled() {
        return mStepValue;
    }

    public double getmDistanceValue() {
        float terrainBuff = RunManager.getInstance().getPlayerTerrainModifier();
        float itemBuff = RunManager.getInstance().getPlayerResistanceMoidifer();
        totalBuff = terrainBuff + itemBuff;
        if (totalBuff < 0) {
            totalBuff = 0;
        }
        return tempStepLength / 100 * totalBuff;
    }

    public double getDistanceLeftValue() {
        return TotalDistance - DistanceValue;
    }

    public double getSpeed() {
        long lastTime = System.currentTimeMillis();
        long deltaTime = lastTime - startTime;
        startTime = lastTime;
        return ((getStepsTraveled() == 0) ? 0.00 : (tempStepLength / deltaTime) * 100);
    }

    //Game logic + Draw Logic
    public void run() {
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            //Game logic
            checkAndPlayDinoApproach();
            checkTrackCompleted();
            checkPlayerDead();
            RunManager.getInstance().updateDistance();
            RunManager.getInstance().checkDistance();
            //RunManager.getInstance().checkTerrainSound();
//            checkTerrain();

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
                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                draw(canvas);

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }
                // End of painting to canvas. system will paint with this canvas,to the surface.
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            checkAndPlayTerrainSound();
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

    public void checkTrackCompleted() {
        if (DistanceValue >= Track.getInstance().getTotalDistance()) {
            this.thread.interrupt();
            finish();
            startActivity(new Intent(getApplicationContext(), LootActivity.class));
        }
    }

    public void checkAndPlayDinoApproach() {
        if(Player.getInstance().getDistance() < Dinosaur.getInstance().getHeadStart()) return;
        double dist = RunManager.getInstance().getDistanceFromPlayer();

        if(dist < 0.2 || dist > 5.1 || Dinosaur.getInstance().getStunned()) {
            SoundManager.getInstance().stopDinoApproach();
        }
        else if(dist > 0.3 && dist < 5) {
            SoundManager.getInstance().playDinoApproach();
        }
    }

    public void checkAndPlayTerrainSound() {
        Tile curTile = Player.getInstance().getCurrentTile();
        if(curTile == null) {
            Player.getInstance().setCurrentTile(Track.getInstance().getTileList().get(0));
            curTile = Player.getInstance().getCurrentTile();
        }
        String curTerrain = curTile.getTerrain();

        if(curTerrain != null && curTerrain.equals(prevTerrain) == false) {
            SoundManager.getInstance().playTerrainSound(curTerrain);
            prevTerrain = curTerrain;
        }
    }


    private void draw(Canvas canvas) {
        if (canvas != null) {
            //canvas.drawColor(getResources().getColor(android.R.color.darker_gray));
            drawEquipment(canvas);
            drawMap(canvas);

            if (drawSprites == null) {
                drawSprites = new DrawSprites(canvas, getResources(), sprites);
            }
            drawSprites.draw();
        }
    }

    //This method draws all the equipment
    public void drawEquipment(Canvas canvas) {
        //Positions
        EquipmentFramePos_X = 700 * scale_width;
        EquipmentFramePos_Y = 910 * scale_height;

        EquipmentPos_X = 760 * scale_width;
        EquipmentPos_Y = 930 * scale_height;

        equipped_head_POS_Y = equipped_head.getHeight();
        equipped_chest_POS_Y = equipped_head.getHeight();
        equipped_shirt_POS_Y = equipped_head.getHeight();
        equipped_pants_POS_Y = equipped_chest.getHeight() + equipped_head.getHeight();
        equipped_shoes_POS_Y = equipped_pants.getHeight() + equipped_chest.getHeight() + equipped_head.getHeight();

        //Draws equipment screen
        //canvas.drawBitmap(character_frame, EquipmentFramePos_X, EquipmentFramePos_Y, paint);
        canvas.drawBitmap(equipped_head, EquipmentPos_X, EquipmentPos_Y, paint);
        canvas.drawBitmap(equipped_cape, EquipmentPos_X, EquipmentPos_Y + equipped_shirt_POS_Y, paint);
        canvas.drawBitmap(equipped_shoulder, EquipmentPos_X, EquipmentPos_Y + equipped_shirt_POS_Y, paint);
        canvas.drawBitmap(equipped_shirt, EquipmentPos_X, EquipmentPos_Y + equipped_shirt_POS_Y, paint);
        canvas.drawBitmap(equipped_chest, EquipmentPos_X, EquipmentPos_Y + equipped_chest_POS_Y, paint);
        canvas.drawBitmap(equipped_pants, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_glove, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_shoes, EquipmentPos_X, EquipmentPos_Y + equipped_shoes_POS_Y, paint);

        //Draws stats screen
        //canvas.drawBitmap(stat_frame, 0, 910 * scale_height, paint);
        //Draws consumable items screen
        //canvas.drawBitmap(items_frame, 0, 1400 * scale_height, paint);

    }

    //This method draws the map
    public void drawMap(Canvas canvas) {
        MapPos_X = 0;
        MapPos_Y = 0;
        getCurrentFrame();
        canvas.drawBitmap(map, frameToDraw, whereToDraw, null);
    }

    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= Track.getInstance().getFrameCount()) {
                    currentFrame = 0;
                }
        }
        //update the left and right values of the source of
        //the next frame on the spritesheet
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

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
        //Laps, distance traveled data
        TotalDistance = Track.getInstance().getTotalDistance();
        Player.getInstance().setHealth(Player.getInstance().getMaxHealth());
        Dinosaur.getInstance().setDistance(0);
        Player.getInstance().setDistance(0);
        RunManager.getInstance().clearTrapLists();
        DistanceValue = 0;
        //Log.d("HeadStart", "" + Dinosaur.getInstance().getHeadStart());
    }

    @Override
    public void onDestroy() {
        unbindStepService();
        stopStepService();

        recycleBitmap(equipped_head);
        recycleBitmap(equipped_shoulder);
        recycleBitmap(equipped_chest);
        recycleBitmap(equipped_shirt);
        recycleBitmap(equipped_glove);
        recycleBitmap(equipped_pants);
        recycleBitmap(equipped_shoes);
        recycleBitmap(equipped_cape);
        recycleBitmap(map);
        recycleBitmap(bm_run_vertical);
        recycleBitmap(bm_run_horizontal);
        recycleBitmap(monster);

        System.gc();

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

    public void setInventoryViews() {
        if (Inventory.getInstance().getEquippedConsumables()[0]!= null) {
            mAmount1.setText("" + Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[0]).getAmount());
            mConsume1.setImageResource(Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[0]).getImageId());
        } else {
            mAmount1.setText("");
        }
        mConsume1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Inventory.getInstance().getEquippedConsumables()[0] != null) {
                    if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[0]) != null) {
                        if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[0]).getAmount() > 0) {
                            Toast.makeText(RunningActivity.this, "You consumed a " + Inventory.getInstance().getEquippedConsumables()[0],
                                    Toast.LENGTH_LONG).show();
                            mAmount1.setTextColor(Color.parseColor("#00FF00"));
                            Inventory.getInstance().useConsumableItem(Inventory.getInstance().getEquippedConsumables()[0]);
                            mAmount1.setText("" + Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[0]));
                        }

                    } else {
                        Toast.makeText(RunningActivity.this, "Ran out of " + Inventory.getInstance().getEquippedConsumables()[0],
                                Toast.LENGTH_LONG).show();
                        mAmount1.setTextColor(Color.parseColor("#FF0000"));
                        mAmount1.setText("0");
                    }
                    if (Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[0])== 0) {
                        mAmount1.setTextColor(Color.parseColor("#FF0000"));
                    }

                }
            }
        });

        if (Inventory.getInstance().getEquippedConsumables()[1] != null) {
            mAmount2.setText("" + Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[1]).getAmount());
            mConsume2.setImageResource(Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[1]).getImageId());
        } else {
            mAmount2.setText("");
        }

        mConsume2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Inventory.getInstance().getEquippedConsumables()[1] != null) {
                    if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[1]) != null) {
                        if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[1]).getAmount() > 0) {
                            Toast.makeText(RunningActivity.this, "You consumed a " + Inventory.getInstance().getEquippedConsumables()[1],
                                    Toast.LENGTH_LONG).show();
                            mAmount2.setTextColor(Color.parseColor("#00FF00"));
                            Inventory.getInstance().useConsumableItem(Inventory.getInstance().getEquippedConsumables()[1]);
                            mAmount2.setText("" + Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[1]));
                        }

                    } else {
                        Toast.makeText(RunningActivity.this, "Ran out of " + Inventory.getInstance().getEquippedConsumables()[1],
                                Toast.LENGTH_LONG).show();
                        mAmount2.setTextColor(Color.parseColor("#FF0000"));
                        mAmount2.setText("0");
                    }
                    if (Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[1]) == 0) {
                        mAmount2.setTextColor(Color.parseColor("#FF0000"));
                    }

                }
            }
        });

        if (Inventory.getInstance().getEquippedConsumables()[2] != null) {
            mAmount3.setText("" + Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[2]).getAmount());
            mConsume3.setImageResource(Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[2]).getImageId());
        } else {
            mAmount3.setText("");
        }

        mConsume3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Inventory.getInstance().getEquippedConsumables()[2] != null) {
                    if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[2]) != null) {
                        if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[2]).getAmount() > 0) {
                            Toast.makeText(RunningActivity.this, "You consumed a " + Inventory.getInstance().getEquippedConsumables()[2],
                                    Toast.LENGTH_LONG).show();
                            mAmount3.setTextColor(Color.parseColor("#00FF00"));
                            Inventory.getInstance().useConsumableItem(Inventory.getInstance().getEquippedConsumables()[2]);
                            mAmount3.setText("" + Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[2]).getAmount());
                        }

                    } else {
                        Toast.makeText(RunningActivity.this, "Ran out of " + Inventory.getInstance().getEquippedConsumables()[2],
                                Toast.LENGTH_LONG).show();
                        mAmount3.setTextColor(Color.parseColor("#FF0000"));
                        mAmount3.setText("0");
                    }
                    if (Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[2]) == 0) {
                        mAmount3.setTextColor(Color.parseColor("#FF0000"));
                    }

                }
                }
        });


       if (Inventory.getInstance().getEquippedConsumables()[3] != null) {
            mAmount4.setText("" + Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[3]).getAmount());
            mConsume4.setImageResource(Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[3]).getImageId());
        } else {
            mAmount4.setText("");
        }
        mConsume4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Inventory.getInstance().getEquippedConsumables()[2] != null) {
                    if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[3]) != null) {
                        if (Inventory.getInstance().inventoryContains(Inventory.getInstance().getEquippedConsumables()[3]).getAmount() > 0) {
                            Toast.makeText(RunningActivity.this, "You consumed a " + Inventory.getInstance().getEquippedConsumables()[3],
                                    Toast.LENGTH_LONG).show();
                            mAmount4.setTextColor(Color.parseColor("#00FF00"));
                            Inventory.getInstance().useConsumableItem(Inventory.getInstance().getEquippedConsumables()[3]);
                            mAmount4.setText("" + Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[3]));
                        }

                    } else {
                        Toast.makeText(RunningActivity.this, "Ran out of " + Inventory.getInstance().getEquippedConsumables()[3],
                                Toast.LENGTH_LONG).show();
                        mAmount4.setTextColor(Color.parseColor("#FF0000"));
                        mAmount4.setText("0");
                    }
                    if (Inventory.getInstance().inventoryItemAmountOf(Inventory.getInstance().getEquippedConsumables()[3]) == 0) {
                        mAmount4.setTextColor(Color.parseColor("#FF0000"));
                    }

                }
            }
        });
    }

    private void recycleBitmap(Bitmap bm) {
        if(bm != null) {
            bm.recycle();
            bm = null;
        }
    }
}
