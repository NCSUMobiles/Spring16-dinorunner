package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.R;

/**
 * Created by jiminfan on 4/14/2016.
 */
public class ItemManager {
    private static ItemManager instance; //Instance of Item manager
    public int imageId;
    public String description;
    int type;
    public float speedBoost;
    public int healAmount;

    public static synchronized ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    /**
     * This method will set all of the variables for the item
     * @param name
     */
    public void setItemVariables(String name) {
        switch(name) {
            //Consumables

            //Equippables
            case "Leather Straps":
                imageId = R.mipmap.default_chest;
                description = "Too bad this hot bod won't scare away the monsters. No boost to your speed";
                type = 0;
                speedBoost = 0;
                healAmount = 0;
                break;
            case "Blue Stiched Vest":
                imageId = R.mipmap.stitched_shirt_blue;
                description = "Blue stiched vest. Lightweight, made by skilled Human craftsmen. +1.5 to your overall speed";
                type = 0;
                speedBoost = 1.5f;
                healAmount = 0;
                break;
            case "FlameWalker Vest":
                imageId = R.mipmap.cloak_of_flames;
                description = "Flame Walker Vest. Imbued by a rare lava gem found by the Dwarven explorer Sognus Bronzebrew. Small flames emit from the fabric that are harmless to the wearer. +3.14 to your overall speed";
                type = 0;
                speedBoost = 3.14f;
                healAmount = 0;
                break;
            case "Yellow Shirt":
                imageId = R.mipmap.default_shirt;
                description = "Standard Yellow shirt. Havn't been washed in days. No boost to your speed";
                type = 0;
                speedBoost = 0;
                healAmount = 0;
                break;
            case "Brown Pants":
                imageId = R.mipmap.default_pants;
                description = "Was brown its original color? Who knows. No boost to your speed";
                type = 0;
                speedBoost = 0;
                healAmount = 0;
                break;
            case "Old Black Boots":
                imageId = R.mipmap.default_shoes;
                description = "A pair of stinky black boots. No boost to your speed";
                type = 0;
                speedBoost = 0;
                healAmount = 0;
                break;
            //Trophys
        }
    }
}
