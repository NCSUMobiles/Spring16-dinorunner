package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 3/26/2016.
 */
public class Track implements Serializable {
    private int imageId;
    private String name;

    public Track(String name, int imageId) {
        this.setName(name);
        this.setImageId(imageId);
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
}
