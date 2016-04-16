package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.MainActivity;
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
    private Bitmap monster;
    public static Typeface oldLondon;


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

    private Paint paint = new Paint();

    //Thread information
    private Thread thread;
    private boolean locker = true;
    private boolean lootCollected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        display = this.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;
        scale_width = width / 1080;
        scale_height = height / 1776;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loot_);

        surfaceView = (SurfaceView) findViewById(R.id.loot_table_surface);
        surfaceHolder = surfaceView.getHolder();

        //Old London Text Style
        oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");

        loot_table_view = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_loot_table);
        loot_table_view = Bitmap.createScaledBitmap(loot_table_view, width, height, true);

        monster = BitmapFactory.decodeResource(getResources(), Dinosaur.getInstance().getImageId());

        mExperienceView = (TextView) findViewById(R.id.experience_loot);
        mItemsLootedView = (TextView) findViewById(R.id.item_loot);
        mMonsterNameView = (TextView) findViewById(R.id.monster_slain_text);
        mGoldLootedView = (TextView) findViewById(R.id.gold_loot);

        mExperienceView.setTypeface(oldLondon);
        mItemsLootedView.setTypeface(oldLondon);
        mMonsterNameView.setTypeface(oldLondon);
        mGoldLootedView.setTypeface(oldLondon);

        mMonsterNameView.setText("Monster Slain: " + Dinosaur.getInstance().getNameOfDino());

        mExperienceView.setTextColor(Color.parseColor("#ffc0cb"));
        mExperienceView.setText("Experience: ");
        Double experienceGained = Dinosaur.getInstance().getExperience();
        Player.getInstance().setExperience(Player.getInstance().getExperience() + experienceGained);
        Double experience = Player.getInstance().getExperience();
        mExperienceView.append(Integer.toString(experience.intValue()));
        mExperienceView.setTextColor(Color.parseColor("#00cd00"));
        mExperienceView.append(" (+" + Integer.toString(experienceGained.intValue()) + ")");

        mGoldLootedView.setTextColor(Color.parseColor("#FFFFFF"));
        mGoldLootedView.setText("Gold: ");
        mGoldLootedView.setTextColor(Color.parseColor("#e5e500"));
        Double goldGained = calculateRandom(Dinosaur.getInstance().getMinGold(), Dinosaur.getInstance().getMaxGold());
        Inventory.getInstance().setGoldAmount(goldGained + Inventory.getInstance().getGoldAmount());
        Double gold = Inventory.getInstance().getGoldAmount();
        mGoldLootedView.append(Integer.toString(gold.intValue()));
        mGoldLootedView.setTextColor(Color.parseColor("#00cd00"));
        mGoldLootedView.append(" (+ " + Integer.toString(goldGained.intValue()) + ")");

        mItemsLootedView.setText("Items Looted: ");
        if(!lootCollected) {
            Item item = calculateDropItem();
            mItemsLootedView.append(item.getAmount() + " " + item.getName());
            Inventory.getInstance().addItem(item, 1);
            lootCollected = true;
        }
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.loot_end_button);
        thread = new Thread(this);
        thread.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.interrupt();
                //finish();
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
            canvas.drawBitmap(monster, 100 * scale_width, 900 * scale_height, paint);
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
}
