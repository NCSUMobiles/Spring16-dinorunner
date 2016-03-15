package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by yao on 3/14/16.
 */
public class MapViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(new MapView(this));
    }
}
