package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DinoPickerActivity extends Activity {
    ListView list;

    String[] dinos = new String[] {
        "T Rex"
    } ;
    Integer[] imageId = new Integer[]{
        R.mipmap.trex
    } ;
    String[] diff = new String[] {
            "Very Hard"
    } ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_picker);

        // Each row in the list stores dinosaur name, image, and difficulty
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<dinos.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("dinos", "Dinosaur : " + dinos[i]);
            hm.put("diff","Difficulty : " + diff[i]);
            hm.put("image", Integer.toString(imageId[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "image","dinos","diff" };

        // Ids of views in listview_layout
        int[] to = { R.id.image,R.id.dinos,R.id.diff};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_single, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }
}


