package com.dino.ncsu.dinorunner;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kevin-Lenovo on 3/26/2016.
 * <p>
 * Class that contains the necessary information that will pertain to the track
 */
public class Track implements Serializable {
    public static Track instance;
    private String trackName;
    private int trackImageId;
    private ArrayList<Tile> tileList;
    private double totalDistance;

    private Track() {

    }

    public void setTrackName(String trackName) { this.trackName = trackName; }

    public String getTrackName() { return trackName; }

    public void setTrackImageId(int trackImageId) { this.trackImageId = trackImageId;}

    public int getTrackImageId() { return trackImageId;}

    public void setTileList(ArrayList<Tile> tileList) { this.tileList = tileList; }

    public ArrayList<Tile> getTileList() { return tileList; }


    public static synchronized Track getInstance() {
        if (instance == null) {
            instance = new Track();
        }
        return instance;
    }
}
