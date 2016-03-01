package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DinoPickerActivity extends Activity {
    ListView list;

    Dinosaur[] dinos = {new Dinosaur("T Rex", 0)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_picker);

        CustomList adapter = new
                CustomList(DinoPickerActivity.this, dinos);
        list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DinoPickerActivity.this, "You Clicked at " + dinos[+position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}
