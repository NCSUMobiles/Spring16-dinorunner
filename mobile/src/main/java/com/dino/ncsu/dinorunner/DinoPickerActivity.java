package com.dino.ncsu.dinorunner;

import android.app.Activity;
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


public class DinoPickerActivity extends Activity {
    private String[] dinos = new String[]{
            "Stegosaurus",
            "Triceratops",
            "Tyrannosaurus Rex"
    };
    private Integer[] imageId = new Integer[]{
            R.mipmap.stego,
            R.mipmap.tri,
            R.mipmap.trex
    };
    private String[] diff = new String[]{
            "A lumbering, fern loving dinosaur. Not very dangerous unless provoked. Easy",
            "A three horned beast. Be wary of its horns and acceleration. Medium",
            "Four words. Run. For. Your. Life! Hard"
    };

    private DinoListAdapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_picker);

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
                HashMap<String, String> item = adapter.getItem(position);
                Dinosaur dino = new Dinosaur(item.get("dinos"), Integer.parseInt(item.get("image")));

                try {
                    Bundle dataBundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), TrackPicker.class);
                    dataBundle.putByteArray("dinoPicked", object2Bytes(dino));
                    intent.putExtras(dataBundle);
                    Toast.makeText(DinoPickerActivity.this, "Selected Dinosaur: " + item.get("dinos"), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            void onItemClick(TrackListAdapter adapter, View v, int position) {

            }
        });

        listView.setAdapter(adapter);
    }
}


