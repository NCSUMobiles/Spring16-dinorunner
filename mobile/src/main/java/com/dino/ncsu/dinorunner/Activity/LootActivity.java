package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dino.ncsu.dinorunner.MainActivity;
import com.dino.ncsu.dinorunner.Managers.LevelManager;
import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.DropTableItem;
import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;
import java.util.Random;

public class LootActivity extends Activity implements Runnable {

    private Bitmap loot_table_view;
    public static Typeface oldLondon;
    public static Typeface roman;


    //Surfaceview information
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    //Display information
    private DisplayMetrics display;
    private int width; //HTC ONE M8 = 1080
    private int height; //HTC ONE M8 = 1776

    //Scale for image size of different screens
    private float scale_width;
    private float scale_height;

    //TextViews
    private TextView mMonsterNameView;
    private TextView mItemsLootedView;
    private TextView mExperienceView;
    private TextView mGoldLootedView;
    private TextView mTitleView;
    private TextView mLevelView;
    private TextView mItemViewCount;

    //Image Views
    private ImageView mItemView;
    private Item item;

    private Paint paint = new Paint();

    //Thread information
    private Thread thread;
    private boolean locker = true;
    private boolean lootCollected = false;

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String PREFERENCE_FILE = "_DinoRunnerUserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        display = this.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;
        scale_width = width / 1080;
        scale_height = height / 1776;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loot_);

        preferenceSettings = getSharedPreferences(PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        surfaceView = (SurfaceView) findViewById(R.id.loot_table_surface);
        surfaceHolder = surfaceView.getHolder();

        //Old London Text Style
        oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");
        roman = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/MorrisRomanBlack.ttf");

        loot_table_view = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_loot_table);
        loot_table_view = Bitmap.createScaledBitmap(loot_table_view, width, height, true);

        mExperienceView = (TextView) findViewById(R.id.experience_loot);
        mItemsLootedView = (TextView) findViewById(R.id.item_loot);
        mMonsterNameView = (TextView) findViewById(R.id.monster_slain_text);
        mGoldLootedView = (TextView) findViewById(R.id.gold_loot);
        mTitleView = (TextView) findViewById((R.id.loot_title));
        mLevelView = (TextView) findViewById(R.id.level_loot);
        mItemViewCount = (TextView) findViewById(R.id.item_loot_image_count);

        mItemView = (ImageView) findViewById(R.id.item_loot_image);

        mExperienceView.setTypeface(roman);
        mItemsLootedView.setTypeface(roman);
        mMonsterNameView.setTypeface(roman);
        mGoldLootedView.setTypeface(roman);
        mLevelView.setTypeface(roman);
        mTitleView.setTypeface(oldLondon);
        mItemViewCount.setTypeface(roman);

        mMonsterNameView.setText("Monster Slain: " + Dinosaur.getInstance().getNameOfDino());

        mExperienceView.setText("Total Experience: ");
        int experienceGained = Dinosaur.getInstance().getExperience();
        int currentXP = preferenceSettings.getInt("_xp", 0);
        int currentGold = preferenceSettings.getInt("_gold", 0);

        //Experience Logic
        int oldLevel = Player.getInstance().getPlayerLevel();
        mLevelView.setText("Player Level: ");
        LevelManager lm = new LevelManager();
        Player.getInstance().setExperience(currentXP + experienceGained);
        int newLevel = lm.convertExpToLevel(Player.getInstance().getExperience());
        mLevelView.append(Integer.toString(newLevel));
        if (newLevel > oldLevel) {
            Toast.makeText(LootActivity.this, "You Leveled Up to Level " + newLevel +" !", Toast.LENGTH_SHORT).show();
            mLevelView.append(" (+" + Integer.toString(newLevel - oldLevel) + ")");
        } else {
            mLevelView.append(" (+" + Integer.toString(0) + ")");
        }

        int experience = Player.getInstance().getExperience();
        mExperienceView.append(Integer.toString(experience));
        mExperienceView.append(" (+" + Integer.toString(experienceGained) + ")");

        mGoldLootedView.setText("Gold: ");
        double goldGained = calculateRandom(Dinosaur.getInstance().getMinGold(), Dinosaur.getInstance().getMaxGold());
        Inventory.getInstance().setGoldAmount(((int) goldGained) + currentGold);
        Double gold = Inventory.getInstance().getGoldAmount();
        mGoldLootedView.append(Integer.toString(gold.intValue()));
        mGoldLootedView.append(" (+" + Integer.toString((int)goldGained) + ")");

        //Preference edit to save player stats
        preferenceEditor.putInt("_xp", Player.getInstance().getExperience());
        preferenceEditor.putInt("_gold", gold.intValue());

        mItemsLootedView.setText("Items Looted: ");
        if(!lootCollected) {
            item = calculateDropItem();
            //mItemsLootedView.append(item.getAmount() + " " + item.getName());
            mItemView.setImageResource(item.getImageId());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LootActivity.this, item.getDescription()
                            ,
                            Toast.LENGTH_LONG).show();
                }
            });
            mItemViewCount.setText("" + item.getAmount());
            Inventory.getInstance().addItem(item.getName(), 1);
            lootCollected = true;
        }

        preferenceEditor.commit();
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.loot_end_button);
        thread = new Thread(this);
        thread.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.interrupt();
                finish();
                Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
                dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dataIntent);
            }
        });


    }

    public void run() {
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }



            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                draw(canvas);

                // End of painting to canvas. system will paint with this canvas,to the surface.
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void draw(Canvas canvas) {
        if(canvas != null) {
            canvas.drawBitmap(loot_table_view, 0, 0, paint);
        }
    }

    private double calculateRandom(double minGold, double maxGold) {
        Random rand= new Random();
        double randomValue = minGold + (maxGold - minGold) * rand.nextDouble();
        return randomValue;
    }

    private Item calculateDropItem() {

        ArrayList<DropTableItem> dropTable = Dinosaur.getInstance().getDropTable();
        //ArrayList<DropTableItem> lootDrops = new ArrayList<DropTableItem>();
        ArrayList<String> lootDrops = new ArrayList<String>();
        int totalChance = 0;

        //Iterate through Arraylist droptable, fill another array based on what is hit
        for (int i = 0; i < dropTable.size(); i++) {
            totalChance += dropTable.get(i).getDropChance();
            for (int j = 0; j < dropTable.get(i).getDropChance(); j++) {
                lootDrops.add(dropTable.get(i).getName());
            }
        }

        Random rand = new Random();
        int randomValue = rand.nextInt(totalChance + 1);
        String pickedItem = lootDrops.get(randomValue);

        for (int i = 0; i < dropTable.size(); i++) {
            if (dropTable.get(i).getName().equals(pickedItem)) {
                double min = dropTable.get(i).getMinAmount();
                double max = dropTable.get(i).getMaxAmount();
                Double itemAmount = calculateRandom(min, max);
                Item item = new Item(pickedItem, itemAmount.intValue());
                Log.d("test", "Successfully added item: " + dropTable.get(i).getName());
                return item;
            }
        }
        Log.d("test", "Somehow we did not drop an item...");
        return null;
    }


    @Override
    public void onDestroy() {
        disposeBitmap(loot_table_view);
        super.onDestroy();
        //Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onBackPressed() {
        thread.interrupt();
        finish();
        Intent dataIntent = new Intent(getApplicationContext(), MainActivity.class);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

    public void disposeBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
