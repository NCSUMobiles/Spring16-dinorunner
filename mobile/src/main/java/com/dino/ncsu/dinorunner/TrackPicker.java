package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dino.ncsu.dinorunner.FileOperations.object2Bytes;

/**
 * This class holds the necessary functionality for the TrackPickActivity
 * When the user picks a track from the list, the previous data along with
 * the track is carried over to the ItemPickActivity.
 */
public class TrackPicker extends Activity {
    //Private variables in this class
    private byte[] dinoByteArray;

    private String[] tracks = new String[]{
            "Forest of A'alath",
            "Desert of Tyndall",
            "Jungle of the Troll King"
    };

    private Integer[] imageId = new Integer[]{
            R.mipmap.forest_a,
            R.mipmap.desert_t,
            R.mipmap.troll
    };

    private String[] desc = new String[]{
            "A small temperate forest near the town of A'alath. Few dangers rests in these peaceful woods. Even the most novice adventurers can navigate through these paths. ",
            "Named after the Elven King Tyndall, the desert of Tyndall is a barren land littered with bandits and quicksand. Even experienced adventurers will find difficulty in not getting lost in these vast sands. ",
            "Few adventurers have ventured into the Troll King's realm and left in one piece. The thick jungle is full of suprises, from man-eating bugs to savage trolls. Do not enter unless you are prepared to die."
    };

    private String[] diff = new String[]{
            "Difficulty: Easy",
            "Difficulty: Medium",
            "Difficulty: Hard"
    };

    // Keys used in Hashmap
    private String[] from = {"image", "tracks", "desc", "diff"};

    // Ids of views in listview_layout
    private int[] to = {R.id.image, R.id.tracks, R.id.desc, R.id.diff};


    private TrackListAdapter adapter;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_picker);

        Bundle bundle = getIntent().getExtras();
        dinoByteArray = bundle.getByteArray("dinoPicked");

        // Each row in the list stores track name, description, difficulty, image
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < tracks.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("tracks", tracks[i]);
            hm.put("desc", desc[i]);
            hm.put("diff", diff[i]);
            hm.put("image", Integer.toString(imageId[i]));
            aList.add(hm);
        }

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TrackPicker.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(mLayoutManager);
        listView.setHasFixedSize(true);

        // Setting the adapter to the listView
        adapter = new TrackListAdapter(getBaseContext(), aList, R.layout.trackpicker_list_single, from, to, new CustomItemClickListener() {
            @Override
            void onItemClick(ItemListAdapter adapter, View v, int position) {
                //Do Nothing
            }

            @Override
            void onItemClick(DinoListAdapter adapter, View v, int position) {
                //Do Nothing
            }

            @Override
            void onItemClick(TrackListAdapter adapter, View v, int position) {
                final HashMap<String, String> item = adapter.getItem(position);
                final Track track = new Track(item.get("tracks"), Integer.parseInt(item.get("image")));

                new AlertDialog.Builder(TrackPicker.this, AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(item.get("tracks"))
                        .setMessage(item.get("desc"))
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Bundle dataBundle = new Bundle();
                                    Intent intent = new Intent(getApplicationContext(), ItemPickActivity.class);
                                    dataBundle.putByteArray("dinoPicked", dinoByteArray);
                                    dataBundle.putByteArray("mapPicked", object2Bytes(track));
                                    intent.putExtras(dataBundle);
                                    Toast.makeText(TrackPicker.this, "Selected Track: " + item.get("tracks"), Toast.LENGTH_SHORT).show();
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
        });

        listView.setAdapter(adapter);
    }

}


