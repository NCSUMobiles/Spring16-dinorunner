package com.dino.ncsu.dinorunner;

import android.content.Intent;
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

    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), TrackPicker.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("dinoPicked", dino);
        dataIntent.putExtras(bundle);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

}
