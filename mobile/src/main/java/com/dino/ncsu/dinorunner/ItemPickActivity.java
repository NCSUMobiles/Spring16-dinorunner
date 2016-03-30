package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.widget.EditText.*;
import static com.dino.ncsu.dinorunner.FileOperations.object2Bytes;

public class ItemPickActivity extends Activity {
    private byte[] dino;
    private byte[] map;

    private String[] items = new String[]{
            "No Helmet",
            "Majestic Helmet",
            "Legacy Helmet",
            "Default Armor",
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
            R.mipmap.default_chest,
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pick);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();
        dino = infoBundle.getByteArray("dinoPicked");
        map = infoBundle.getByteArray("mapPicked");

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

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
                        try {
                            int distance = Integer.parseInt(((EditText) ((AlertDialog) dialog).findViewById(R.id.dist_value)).getText().toString());

                            if (distance < 100 || distance > 40000)
                                distance = ((SeekBar) ((AlertDialog) dialog).findViewById(R.id.distance_picker)).getProgress() + 100;

                            int laps = ((NumberPicker) ((AlertDialog) dialog).findViewById(R.id.laps_picker)).getValue();

                            EquippedItems equippedItems = EquippedItems.getInstance();

                            for (int i = 0; i < 5; i++) {
                                HashMap<String, String> iMap = mListAdapter.getItem(i);
                                RunningItem item = new RunningItem(iMap.get(from[1]),
                                        Integer.parseInt(iMap.get(from[0])),
                                        Double.parseDouble(iMap.get(from[3])));

                                equippedItems.setItemAtIndex(i, item);
                            }

                            Bundle dataBundle = new Bundle();
                            Intent intent = new Intent(getApplicationContext(), RunningActivity.class);
                            dataBundle.putByteArray("dinoPicked", dino);
                            dataBundle.putByteArray("mapPicked", map);
                            dataBundle.putInt("distancePicked", distance);
                            dataBundle.putInt("lapsPicked", laps);
                            dataBundle.putByteArray("itemsPicked", object2Bytes(equippedItems));

                            intent.putExtras(dataBundle);
                            //Toast.makeText(getApplicationContext(), "Start Running", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), TrackPicker.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("dinoPicked", dino);
        dataIntent.putExtras(bundle);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ItemPick Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dino.ncsu.dinorunner/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ItemPick Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dino.ncsu.dinorunner/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
