package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.R;

/**
 * Created by jiminfan on 4/14/2016.
 */
public class ItemManager {
    private static ItemManager instance; //Instance of Item manager

    public static synchronized ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    /**
     * This method will set all of the variables for the item
     * @param item The reference to the item we watn to modify
     */
    public void setItemVariables(Item item) {
        switch(item.getName()) {
            //Consumables
            case "Fresh Apple":
                item.setImageId(R.mipmap.fresh_apple);
                item.setDescription("A succulent red apple. Heals 10 HP.");
                item.setType(0);
                item.setHealAmount(10);
                item.setBuyAmount(10);
                item.setSellAmount(5);
                item.setRarity(0);
                break;
            //Equippables
            case "No Helmet":
                item.setImageId(R.mipmap.default_head);
                item.setDescription("Who needs helmets?. No boost to your speed");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(12);
                item.setBuyAmount(24);
                item.setRarity(0);
                break;
            case "Leather Straps":
                item.setImageId(R.mipmap.default_chest);
                item.setDescription("Too bad this hot bod won't scare away the monsters. No boost to your speed");
                item.setType(1);
                item.setSellAmount(12);
                item.setBuyAmount(24);
                item.setRarity(0);
                break;
            case "Blue Stiched Vest":
                item.setImageId(R.mipmap.stitched_shirt_blue);
                item.setDescription("Blue stiched vest. Lightweight, made by skilled Human craftsmen. +1.5 to your overall speed");
                item.setType(1);
                item.setSpeedBoost(1.5f);
                item.setSellAmount(100);
                item.setBuyAmount(200);
                item.setRarity(1);
                break;
            case "FlameWalker Vest":
                item.setImageId(R.mipmap.cloak_of_flames);
                item.setDescription("Flame Walker Vest. Imbued by a rare lava gem found by the Dwarven explorer Sognus Bronzebrew. Small flames emit from the fabric that are harmless to the wearer. +3.14 to your overall speed");
                item.setType(1);
                item.setSpeedBoost(3.14f);
                item.setSellAmount(5000);
                item.setBuyAmount(10000);
                item.setRarity(3);
                break;
            case "Yellow Shirt":
                item.setImageId(R.mipmap.default_shirt);
                item.setDescription("Standard Yellow shirt. Havn't been washed in days. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                break;
            case "Brown Pants":
                item.setImageId(R.mipmap.default_pants);
                item.setDescription("Was brown its original color? Who knows. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                break;
            case "Old Black Boots":
                item.setImageId(R.mipmap.default_shoes);
                item.setDescription("A pair of stinky black boots. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                break;
            //Trophys
            case "Copper Ore":
                item.setImageId(R.mipmap.copper_ore);
                item.setDescription("A lump of Copper Ore");
                item.setType(2);
                item.setSellAmount(10);
                item.setBuyAmount(20);
                item.setRarity(0);
                break;
            case "Tin Ore":
                item.setImageId(R.mipmap.tin_ore);
                item.setDescription("A lump of Tin Ore");
                item.setType(2);
                item.setSellAmount(10);
                item.setBuyAmount(20);
                item.setRarity(0);
                break;
            case "Iron Ore":
                item.setImageId(R.mipmap.iron_ore);
                item.setDescription("A lump of Iron Ore");
                item.setType(2);
                item.setSellAmount(100);
                item.setBuyAmount(200);
                item.setRarity(1);
                break;
        }
    }

//    //Sets Manager's values back to original values
//    //Reset any variable that is NOT somethign set by all items
//    public void clearInstance() {
//        speedBoost = 0;
//        tempSpeedBoost = 0;
//        healAmount = 0;
//    }
}
