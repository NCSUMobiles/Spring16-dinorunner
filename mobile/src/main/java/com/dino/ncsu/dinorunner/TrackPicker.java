package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dino.ncsu.dinorunner.FileOperations.object2Bytes;


public class TrackPicker extends Activity {
    ListView list;
    byte[] dinoByteArray;

    String[] tracks = new String[]{
            "Forest of A'alath",
            "Desert of Tyndall",
            "Jungle of the Troll King"
    };
    Integer[] imageId = new Integer[]{
            R.mipmap.forest_a,
            R.mipmap.desert_t,
            R.mipmap.troll
    };
    String[] desc = new String[]{
            "A small temperate forest near the town of A'alath. Few dangers rests in these peaceful woods. Even the most novice adventurers can navigate through these paths. ",
            "Named after the Elven King Tyndall, the desert of Tyndall is a barren land littered with bandits and quicksand. Even experienced adventurers will find difficulty in not getting lost in these vast sands. ",
            "Few adventurers have ventured into the Troll King's realm and left in one piece. The thick jungle is full of suprises, from man-eating bugs to savage trolls. Do not enter unless you are prepared to die."
    };

    String[] diff = new String[] {
            "Difficulty: Easy",
            "Difficulty: Medium",
            "Difficulty: Hard"
    };

    /**
     * Called when the activity is first created.
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

        // Keys used in Hashmap
        String[] from = {"image", "tracks", "desc", "diff"};

        // Ids of views in listview_layout
        int[] to = {R.id.image, R.id.tracks, R.id.desc, R.id.diff};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.trackpicker_list_single, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> item = (HashMap<String, String>) parent.getAdapter().getItem(position);

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
                                    dataBundle.putByteArray("mapPicked", object2Bytes(item));
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
    }

}


