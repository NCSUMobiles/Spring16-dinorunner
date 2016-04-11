package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 3/26/2016.
 * <p>
 * Class that contains the necessary information that will pertain to the track
 */
public class Track implements Serializable {
    //private variables for this class
    private int imageId;
    private String name;
    private int trackImageId;
    /**
     * Constructor for the Track class
     *
     * @param name    Name of the track
     * @param imageId ID of the image to display for the track
     * @param trackImageId ID of the track image
     */
    public Track(String name, int imageId, int trackImageId) {
        this.setName(name);
        this.setImageId(imageId);
        this.setTrackImageId(trackImageId);
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackImageId() { return trackImageId; }

    public void setTrackImageId(int trackImageId) {
        this.trackImageId = trackImageId;
    }
}
