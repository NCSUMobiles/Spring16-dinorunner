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
        item.setEquipSlot("NA");
        item.setHealAmount(0);
        item.setTempSpeedBoost(0);
        item.setSpeedBoost(0);
        item.setVisible(true);
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
            //Equippable Head Items
            case "Leather Bandanna":
                item.setImageId(R.mipmap.leather_bandanna);
                item.setDescription("Makes you look like a monk. No boost to your speed");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(12);
                item.setBuyAmount(24);
                item.setRarity(0);
                item.setEquipSlot("HEAD");
                break;
            case "No Head Item":
                item.setImageId(R.mipmap.default_head);
                item.setDescription("Who needs helmets?");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("HEAD");
                item.setVisible(false);
                break;
            //Equippable Shoulder Item
            case "No Shoulder Item":
                item.setImageId(R.mipmap.default_shoulders);
                item.setDescription("No shoulder pads, no problem!");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("SHOULDERS");
                item.setVisible(false);
                break;
            //Equippable Chest Item
            case "No Chest Item":
                item.setImageId(R.mipmap.default_armor);
                item.setDescription("Who needs armor?");
                item.setType(1);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("CHEST");
                item.setVisible(false);
                break;
            case "Leather Straps":
                item.setImageId(R.mipmap.leather_straps);
                item.setDescription("Better than no armor. No boost to your speed");
                item.setType(1);
                item.setSellAmount(12);
                item.setBuyAmount(24);
                item.setRarity(0);
                item.setEquipSlot("CHEST");
                break;
            case "Blue Stiched Vest":
                item.setImageId(R.mipmap.stitched_shirt_blue);
                item.setDescription("Blue stiched vest. Lightweight, made by skilled Human craftsmen. +1.5 to your overall speed");
                item.setType(1);
                item.setSpeedBoost(1.5f);
                item.setSellAmount(100);
                item.setBuyAmount(200);
                item.setRarity(1);
                item.setEquipSlot("CHEST");
                break;
            case "FlameWalker Vest":
                item.setImageId(R.mipmap.cloak_of_flames);
                item.setDescription("Flame Walker Vest. Imbued by a rare lava gem found by the Dwarven explorer Sognus Bronzebrew. Small flames emit from the fabric that are harmless to the wearer. +3.14 to your overall speed");
                item.setType(1);
                item.setSpeedBoost(3.14f);
                item.setSellAmount(5000);
                item.setBuyAmount(10000);
                item.setRarity(3);
                item.setEquipSlot("CHEST");
                break;
            //Equippable Shirts
            case "No Shirt Item":
                item.setImageId(R.mipmap.default_shirt);
                item.setDescription("Who needs undergarments?");
                item.setType(1);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("SHIRT");
                item.setVisible(false);
                break;
            case "Yellow Shirt":
                item.setImageId(R.mipmap.yellow_shirt);
                item.setDescription("Standard Yellow shirt. Havn't been washed in days. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                item.setEquipSlot("SHIRT");
                break;
            //Equippable Gloves:
            case "No Gloves Item":
                item.setImageId(R.mipmap.default_shirt);
                item.setDescription("Who needs gloves?");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("GLOVES");
                item.setVisible(false);
                break;
            //Equippable Pants
            case "No Leg Item":
                item.setImageId(R.mipmap.default_pants);
                item.setDescription("Breezy!");
                item.setType(1);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("LEGS");
                break;
            case "Brown Pants":
                item.setImageId(R.mipmap.brown_pants);
                item.setDescription("Was brown its original color? Who knows. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                item.setEquipSlot("LEGS");
                break;
            //Equippable Boots
            case "No Feet Item":
                item.setImageId(R.mipmap.default_shoes);
                item.setDescription("Ouch!");
                item.setType(1);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("FEET");
                break;
            case "Old Black Boots":
                item.setImageId(R.mipmap.black_boots);
                item.setDescription("A pair of stinky black boots. No boost to your speed");
                item.setType(1);
                item.setSellAmount(1);
                item.setBuyAmount(5);
                item.setRarity(0);
                item.setEquipSlot("FEET");
                break;
            //Equippable Cape
            case "No Cape Item":
                item.setImageId(R.mipmap.default_shirt);
                item.setDescription("Who needs helmets?");
                item.setType(1);
                item.setSpeedBoost(0);
                item.setSellAmount(0);
                item.setBuyAmount(0);
                item.setRarity(0);
                item.setEquipSlot("HEAD");
                item.setVisible(false);
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
}
