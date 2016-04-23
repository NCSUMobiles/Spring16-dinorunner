package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class holds the necessary functionality for the DicoPickerActivity
 * When the user picks a Monster from the list, the data of the monster
 * is carried over to the TrackPickerActivity.
 */
public class DinoPickerActivity extends Activity {
    //private variables for this class
    public static String[] dinos = new String[]{
            "Goblin",
            "Skeleton Spearman",
            "Forest Giant"
    };
    public static Integer[] imageId = new Integer[]{
            R.drawable.monster_goblin,
            R.drawable.monster_skele_spearman,
            R.drawable.monster_forest_giant
    };
    private String[] diff = new String[]{
            "Goblins are more mischievous than dangerous. They patrol around to find weak adventurers to ambush. Easy",
            "Sp00ky! Medium",
            "Peaceful until provoked. Just being there is provoking him. Hard"
    };

    private DinoListAdapter adapter;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_picker);
        Typeface oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");

        ((TextView)findViewById(R.id.textView)).setTypeface(oldLondon);

        // Each row in the list stores dinosaur name, image, and difficulty
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < dinos.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("dinos", dinos[i]);
            hm.put("diff", diff[i]);
            hm.put("image", Integer.toString(imageId[i]));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"image", "dinos", "diff"};

        // Ids of views in listview_layout
        int[] to = {R.id.image, R.id.dinos, R.id.diff};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        // Getting a reference to listview of main.xml layout file
        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DinoPickerActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(mLayoutManager);
        listView.setHasFixedSize(true);

        // Setting the adapter to the listView
        adapter = new DinoListAdapter(getBaseContext(), aList, R.layout.dinopicker_list_single, from, to, new CustomItemClickListener() {
            @Override
            void onItemClick(ItemListAdapter adapter, View v, int position) {
                //does nothing for now
            }

            @Override
            public void onItemClick(DinoListAdapter adapter, View v, int position) {
//                HashMap<String, String> item = adapter.getItem(position);
//                Dinosaur.getInstance().setNameOfDino(item.get("dinos"));
//                Dinosaur.getInstance().setImageId(Integer.parseInt(item.get("image")));
//                DinoManager dinoManager = new DinoManager();
//                dinoManager.setDinos();
//
//                try {
//                    Bundle dataBundle = new Bundle();
//                    Intent intent = new Intent(getApplicationContext(), TrackPicker.class);
//                    dataBundle.putByteArray("dinoPicked", object2Bytes(Dinosaur.getInstance()));
//                    intent.putExtras(dataBundle);
//                    Toast.makeText(DinoPickerActivity.this, "Selected Dinosaur: " + item.get("dinos"), Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            void onItemClick(TrackListAdapter adapter, View v, int position) {

            }
        });

        listView.setAdapter(adapter);
    }
}


