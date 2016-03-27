package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 3/1/2016.
 */
public class RunningItem implements Serializable {
    private String name;
    private Integer imageId;
    private double speedBoost;

    public RunningItem(String name, Integer imageId, double speedBoost) {
        this.name = name;
        this.imageId = imageId;
        this.speedBoost = speedBoost;
    }
    public RunningItem() {
        setName("Default Item");
        setImageId(0);
        setSpeedBoost(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public double getSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(double speedBoost) {
        this.speedBoost = speedBoost;
    }
}
