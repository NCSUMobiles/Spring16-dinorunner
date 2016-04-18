package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.Managers.ItemManager;
import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Pedometer.RunningActivity;
import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.widget.EditText.OnClickListener;

/**
 * This class holds the necessary functionality for the ItemPickActivity
 * When the user picks items from the list, the previous data along with
 * the list of equipped items is carried over to the RunningActivity.
 */
public class ItemPickActivity extends Activity {
    //private variables for this class
    private String[] items = new String[]{
            "No Helmet",
            "Majestic Helmet",
            "Legacy Helmet",
            "Leather Straps",
            "Blue Stitched Vest",
            "Flame Walker Vest",
            "Yellow Shirt",
            "Majestic Shirt",
            "Epic Shirt",
            "Brown Pants",
            "Epic Pants",
            "Legacy Pants",
            "Old Black Boots",
            "Fast Shoes",
            "Ultimate Shoes"
    };

    private Integer[] imageId = new Integer[]{
            R.mipmap.default_head,
            R.mipmap.runman,
            R.mipmap.runman,
            R.mipmap.default_armor,
            R.mipmap.stitched_shirt_blue,
            R.mipmap.cloak_of_flames,
            R.mipmap.default_shirt,
            R.mipmap.runman,
            R.mipmap.runman,
            R.mipmap.default_pants,
            R.mipmap.runman,
            R.mipmap.runman,
            R.mipmap.default_shoes,
            R.mipmap.runman,
            R.mipmap.runman

    };
    private String[] desc = new String[]{
            "Who needs helmets?. No boost to your speed",
            "Epic Item. +1.5 to your overall speed",
            "Legendary Item. +3.14 to your overall speed",
            "Too bad this hot bod won't scare away the monsters. No boost to your speed",
            "Blue stiched vest. Lightweight, made by skilled Human craftsmen. +1.5 to your overall speed",
            "Flame Walker Vest. Imbued by a rare lava gem found by the Dwarven explorer Sognus Bronzebrew. Small flames emit from the fabric that are harmless to the wearer. +3.14 to your overall speed",
            "Standard shirt. Havn't been washed in days. No boost to your speed",
            "Epic Item. +1.5 to your overall speed",
            "Legendary Item. +3.14 to your overall speed",
            "Was brown its original color? Who knows. No boost to your speed",
            "Epic Item. +1.5 to your overall speed",
            "Legendary Item. +3.14 to your overall speed",
            "A pair of stinky black boots. No boost to your speed",
            "Epic Item. +1.5 to your overall speed",
            "Legendary Item. +3.14 to your overall speed"
    };
    private Double[] boosts = new Double[]{
            0.0,
            1.5,
            3.14,
            0.0,
            1.5,
            3.14,
            0.0,
            1.5,
            3.14,
            0.0,
            1.5,
            3.14,
            0.0,
            1.5,
            3.14
    };

    private String[] itemTypes = new String[]{"Choose your Helmet", "Choose your Armor", "Choose your Shirt", "Choose your Pants", "Choose your Shoes"};

    private List<Integer> defaultIndices = Arrays.asList(0, 3, 6, 9, 12, items.length);

    private RecyclerView lView;
    private List<HashMap<String, String>> itemList;
    private List<HashMap<String, String>> allItems;

    //Keys in Hashmap
    private String[] from = {"image", "name", "desc", "boost"};

    //IDs of views in listview layout
    private int[] to = {R.id.image, R.id.name, R.id.desc, R.id.boost};

    private ItemListAdapter mListAdapter;
    static Inventory inventory = Inventory.getInstance();

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pick);

        Typeface oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");

        ((TextView)findViewById(R.id.textView)).setTypeface(oldLondon);
        //Each row of the list stores item name, image and description
        if (itemList == null) {
            itemList = new ArrayList<>();
            allItems = new ArrayList<>();

            for (int i = 0; i < items.length; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("image", Integer.toString(imageId[i]));
                hm.put("name", items[i]);
                hm.put("desc", desc[i]);
                hm.put("boost", Double.toString(boosts[i]));
                allItems.add(hm);

                if (defaultIndices.indexOf(i) != -1)
                    itemList.add(hm);
            }

            lView = (RecyclerView) findViewById(R.id.item_list_view);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemPickActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            lView.setItemAnimator(new DefaultItemAnimator());
            lView.setLayoutManager(mLayoutManager);

            mListAdapter = new ItemListAdapter(getBaseContext(), itemList, R.layout.itempicker_list_single, from, to, new CustomItemClickListener() {
                @Override
                public void onItemClick(ItemListAdapter adapter, View v, int position) {
                    createItemDialog(position, adapter);
                }

                @Override
                void onItemClick(DinoListAdapter adapter, View v, int position) {
                    //Do Nothing
                }

                @Override
                void onItemClick(TrackListAdapter adapter, View v, int position) {
                    //Do Nothing
                }
            });
            lView.setAdapter(mListAdapter);
        }
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.start_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clicked the button!", Toast.LENGTH_LONG).show();
                createRunDialog();
            }
        });
    }

    /**
     * Creates the dialog to choose an item for the specific category
     *
     * @param position The type of item to choose in the dialog
     * @param adapter  The adapter that holds the items currently equipped
     */
    private void createItemDialog(final int position, ItemListAdapter adapter) {
        adapter.getItem(position);
        View dialogView = getLayoutInflater().inflate(R.layout.list_items, null);

        RecyclerView rView = (RecyclerView) dialogView.findViewById(R.id.listView2);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemPickActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(mLayoutManager);
        final List<HashMap<String, String>> newList = allItems.subList(defaultIndices.get(position), defaultIndices.get(position + 1));

        final AlertDialog newDialog = new AlertDialog.Builder(ItemPickActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(itemTypes[position])
                .setView(dialogView)
                .create();

        rView.setAdapter(new ItemListAdapter(getBaseContext(), newList, R.layout.itempicker_list_single, from, to, new CustomItemClickListener() {
            @Override
            public void onItemClick(ItemListAdapter adapter, View v, int position2) {
                mListAdapter.setItem(position, newList.get(position2));
                newDialog.dismiss();
            }

            @Override
            void onItemClick(DinoListAdapter adapter, View v, int position) {
                //Do nothing for now
            }

            @Override
            void onItemClick(TrackListAdapter adapter, View v, int position) {
                //Do Nothing
            }
        }));

        newDialog.show();
    }

    /**
     * Creates the dialog necessary to set the distance
     * and laps to run for the run
     */
    private void createRunDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_view_options, null);

        final SeekBar pickDist = (SeekBar) dialogView.findViewById(R.id.distance_picker);
        pickDist.setMax(39900);
        pickDist.setProgress(0);
        pickDist.incrementProgressBy(50);
        final EditText distValueText = (EditText) dialogView.findViewById(R.id.dist_value);
        distValueText.setSelection(distValueText.length());

        distValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    int j = Integer.parseInt(s.toString());
                    if (j >= 100 && j <= 40000)
                        pickDist.setProgress(j - 100);  //for the seekbar, 0-39900
                } else {
                    pickDist.setProgress(0);  //for the seekbar, 0-39900
                    distValueText.setText("100");
                }
            }
        });

        pickDist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
                progress /= 50;
                progress *= 50;
                int posOfCursor = distValueText.getSelectionStart();
                distValueText.setText(String.valueOf(progress + 100));
                distValueText.setSelection(posOfCursor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        NumberPicker pickLaps = (NumberPicker) dialogView.findViewById(R.id.laps_picker);
        pickLaps.setMinValue(1);
        pickLaps.setMaxValue(30);

        new AlertDialog.Builder(ItemPickActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Options")
                .setView(dialogView)
                .setPositiveButton("Run", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int distance = Integer.parseInt(((EditText) ((AlertDialog) dialog).findViewById(R.id.dist_value)).getText().toString());

                        if (distance < 100 || distance > 40000)
                            distance = ((SeekBar) ((AlertDialog) dialog).findViewById(R.id.distance_picker)).getProgress() + 100;

                        int laps = ((NumberPicker) ((AlertDialog) dialog).findViewById(R.id.laps_picker)).getValue();

                        for (int i = 0; i < 5; i++) {
                            HashMap<String, String> iMap = mListAdapter.getItem(i);

                            //EquippedItems.getInstance().setItemAtIndex(i, item);
//                            Item item = new Item(iMap.get(from[1]), 1);
//                            //This will set all of the other values for the item
//                            ItemManager.getInstance().setItemVariables(item);

//                            Log.d("name", item.getName());
//                            Log.d("amt", "" + item.getAmount());
//                            Log.d("descript", item.getDescription());
//                            Log.d("ItemType", "" + item.getType());

                            inventory.addItem(iMap.get(from[1]), 1);
//                            Log.d("ItemAdded", "" + inventory.getEquippableItems().size());
//                            Log.d("MapAdded", "" + inventory.getEquippableItemsMap().size());
                        }

                        Bundle dataBundle = new Bundle();
                        Intent intent = new Intent(getApplicationContext(), RunningActivity.class);
                        dataBundle.putInt("distancePicked", distance);
                        dataBundle.putInt("lapsPicked", laps);

                        intent.putExtras(dataBundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing for right now
                    }
                })
                .show();
    }
}
