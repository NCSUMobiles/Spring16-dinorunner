package com.dino.ncsu.dinorunner.Objects;

import java.io.Serializable;
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
    private double totalDistance;       //the total distance of track (in meter)
    private float totalLength;         //the total length of track (in pixel)
    private int frameCount; //Frames for animation
    private int insurance; //How much money the track takes away if you fail

    private Track() {
        tileList = new ArrayList<Tile>();
    }

    public int getFrameCount() { return frameCount; }
    public void setFrameCount(int frameCount) { this.frameCount = frameCount; }

    public int getInsurance() { return insurance; }
    public void setInsurance(int insurance) { this.insurance = insurance; }

    public void setTrackName(String trackName) { this.trackName = trackName; }
    public String getTrackName() { return trackName; }

    public void setTrackImageId(int trackImageId) { this.trackImageId = trackImageId;}
    public int getTrackImageId() { return trackImageId;}

    public void setTileList(ArrayList<Tile> tileList) { this.tileList = tileList; }
    public ArrayList<Tile> getTileList() { return tileList; }

    public void setTotalDistance(double totalDistance) {this.totalDistance = totalDistance;}
    public double getTotalDistance() {return totalDistance;}

    public float getTotalLength() {
        totalLength = 0;
        for(int i = 0; i < tileList.size(); i++) {
            totalLength += tileList.get(i).getLength();
        }
        return totalLength;
    }

    public static synchronized Track getInstance() {
        if (instance == null) {
            instance = new Track();
        }
        return instance;
    }
}
