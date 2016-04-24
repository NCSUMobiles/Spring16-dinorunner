package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dino.ncsu.dinorunner.Activity.DinoPickerActivity;
import com.dino.ncsu.dinorunner.Activity.PlayerStatsActivity;
import com.dino.ncsu.dinorunner.Activity.TrackPicker;
import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Player;

/**
 * This class holds the necessary functionality for the MainActivity.
 */
public class MainActivity extends Activity {

    private String[] items = new String[]{
            "No Head Item",
            "Leather Bandanna",
            "No Shoulder Item",
            "No Chest Item",
            "Leather Straps",
            "Blue Stitched Vest",
            "Flame Walker Vest",
            "No Shirt Item",
            "Yellow Shirt",
            "No Gloves Item",
            "No Leg Item",
            "Brown Pants",
            "No Feet Item",
            "Old Black Boots",
            "No Cape Item"
    };


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Player.getInstance().checkNewGame();

        for (int i = 0; i < items.length; i++) {
            Inventory.getInstance().addItem(items[i], 1);
        }


//        Log.d("test", "Equippable Item Map size: " + Inventory.getInstance().getEquippableItemsMap().size());
//        Log.d("test", "Equippable Item size: " + Inventory.getInstance().getEquippableItems().size());
//        Log.d("test", "Equipped Item size: " + Inventory.getInstance().getEquippedItems().size());
//        for (int i = 0; i < Inventory.getInstance().getEquippableItemsMap().size(); i++) {
//            Log.d("test", "Array of Equips: " + Inventory.getInstance().getEquippableItemsMap().get(i));
//        }
//
//        for(int j = 0; j < Inventory.getInstance().getEquippedItemsMap().size(); j++) {
//            Log.d("test", "Array of Equipped items: " + Inventory.getInstance().getEquippedItemsMap().get(j));
//        }

        Button runButton = (Button) findViewById(R.id.run_button);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TrackPicker.class));
            }
        });

        Button statsButton = (Button) findViewById(R.id.stats_button);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PlayerStatsActivity.class));
            }
        });

        Button mapViewButton = (Button) findViewById(R.id.gallery_button);
        mapViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DinoPickerActivity.class));
            }
        });
    }

    /**
     * Pulls up the menu to configure the default options for the user
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Checks if item is already clicked
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
