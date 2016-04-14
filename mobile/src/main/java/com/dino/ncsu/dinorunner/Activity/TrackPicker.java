package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.dino.ncsu.dinorunner.Managers.DinoManager;
import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.R;
import com.dino.ncsu.dinorunner.Objects.Track;
import com.dino.ncsu.dinorunner.Managers.TrackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class holds the necessary functionality for the TrackPickActivity
 * When the user picks a track from the list, the previous data along with
 * the track is carried over to the ItemPickActivity.
 */
public class TrackPicker extends Activity {
    //Private variables in this class
    //private byte[] dinoByteArray;

    //Scale for image size of different screens
    private float scale_width;
    private float scale_height;

    //Display information
    private DisplayMetrics display;
    private int width; //HTC ONE M8 = 1080
    private int height; //HTC ONE M8 = 1776

    private double[][] probs = {
            {.95, .999},
            {.5, .9},
            {.1, .4}
    };

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

    private Integer[] trackImageId = new Integer[]{
            R.mipmap.track1,
            R.mipmap.track1,
            R.mipmap.track1
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

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;
        scale_width = width / 1080;
        scale_height = height / 1776;

//        Bundle bundle = getIntent().getExtras();
//        dinoByteArray = bundle.getByteArray("dinoPicked");

        // Each row in the list stores track name, description, difficulty, image
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < tracks.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("tracks", tracks[i]);
            hm.put("desc", desc[i]);
            hm.put("diff", diff[i]);
            hm.put("image", Integer.toString(imageId[i]));
            hm.put("trackImage", Integer.toString(trackImageId[i]));
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
            void onItemClick(TrackListAdapter adapter, View v, final int position) {

                final HashMap<String, String> item = adapter.getItem(position);
                Track.getInstance().setTrackName(item.get("tracks"));
                Track.getInstance().setTrackImageId(Integer.parseInt(item.get("trackImage")));
                TrackManager tm = new TrackManager();
                tm.setTrack(scale_width, scale_height);

                new AlertDialog.Builder(TrackPicker.this, AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(item.get("tracks"))
                        .setMessage(item.get("desc"))
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Bundle dataBundle = new Bundle();

                                //dataBundle.putByteArray("dinoPicked", dinoByteArray);
                                //intent.putExtras(dataBundle);
                                //calcuate probabilities here depending on map
                                Random ran = new Random();
                                double prob = ran.nextDouble();

                                if (prob <= probs[position][0]) {
                                    Dinosaur.getInstance().setNameOfDino(DinoPickerActivity.dinos[0]);
                                    Dinosaur.getInstance().setImageId(DinoPickerActivity.imageId[0]);
                                } else if (prob <= probs[position][1]) {
                                    Dinosaur.getInstance().setNameOfDino(DinoPickerActivity.dinos[1]);
                                    Dinosaur.getInstance().setImageId(DinoPickerActivity.imageId[1]);
                                } else {
                                    Dinosaur.getInstance().setNameOfDino(DinoPickerActivity.dinos[2]);
                                    Dinosaur.getInstance().setImageId(DinoPickerActivity.imageId[2]);
                                }

                                DinoManager dinoManager = new DinoManager();
                                dinoManager.setDinos();
                                Toast.makeText(TrackPicker.this, "Selected Track: " + item.get("tracks"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ItemPickActivity.class));
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

