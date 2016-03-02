package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DinoPickerActivity extends Activity {
    ListView list;

    String[] dinos = new String[]{
            "Stegosaurus",
            "Triceratops",
            "Tyrannosaurus Rex"
    };
    Integer[] imageId = new Integer[]{
            R.mipmap.stego,
            R.mipmap.tri,
            R.mipmap.trex
    };
    String[] diff = new String[]{
            "A lumbering, fern loving dinosaur. Not very dangerous unless provoked. Easy",
            "A three horned beast. Be wary of its horns and acceleration. Medium",
            "Four words. Run. For. Your. Life! Hard"
    };

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
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_single, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getAdapter().getItem(position);
                Toast.makeText(DinoPickerActivity.this, "DinoName: " + item.get("dinos"), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


