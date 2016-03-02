package com.dino.ncsu.dinorunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ItemPickActivity extends AppCompatActivity {
    private byte[] dino;
    private byte[] map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pick);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();
        dino = infoBundle.getByteArray("dinoPicked");
        map = infoBundle.getByteArray("mapPicked");
    }

}
