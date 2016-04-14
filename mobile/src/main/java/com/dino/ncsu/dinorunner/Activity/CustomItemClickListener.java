package com.dino.ncsu.dinorunner.Activity;

import android.view.View;

/**
 * Created by Kevin-Lenovo on 3/25/2016.
 * <p>
 * This class contains the interface for onItemClick methods
 * to be used inside of each custom ListAdapter for this app
 */
public abstract class CustomItemClickListener {
    //Item Click Event to be triggered on an item click
    abstract void onItemClick(ItemListAdapter adapter, View v, int position);

    abstract void onItemClick(DinoListAdapter adapter, View v, int position);

    abstract void onItemClick(TrackListAdapter adapter, View v, int position);
}
