package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 3/1/2016
 * <p>
 * Class that holds the essential data for the RunningItem class
 */
public class RunningItem implements Serializable {
    //Private variables in this class
    private String name;
    private Integer imageId;
    private double speedBoost;

    /**
     * Constructors for the RunningItem Class
     *
     * @param name       The name of the item
     * @param imageId    The id of the image to display in the listviews
     * @param speedBoost The boost of speed given to the player when equipping this item
     */
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
