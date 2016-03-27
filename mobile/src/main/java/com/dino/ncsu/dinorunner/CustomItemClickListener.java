package com.dino.ncsu.dinorunner;

import android.view.View;

/**
 * Created by Kevin-Lenovo on 3/25/2016.
 */
public abstract class CustomItemClickListener {
    //Item Click Event to be triggered on an item click
    abstract void onItemClick(ItemListAdapter adapter, View v, int position);
    abstract void onItemClick(DinoListAdapter adapter, View v, int position);
    abstract void onItemClick(TrackListAdapter adapter, View v, int position);
}
