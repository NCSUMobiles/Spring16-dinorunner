package com.dino.ncsu.dinorunner.Objects;

import com.dino.ncsu.dinorunner.Managers.ItemManager;

import java.io.Serializable;

/**
 * Created by jiminfan on 4/13/2016.
 */
public class Item implements Serializable {
    private String name;
    private int imageId;
    private String description;
    private int type;
    private int amount;

    private float speedBoost;
    private int healAmount;

    public Item (String name,int amount) {
        this.name = name;
        this.amount = amount;

        //This will set all of the other values for the item
        ItemManager.getInstance().setItemVariables(name);

        this.imageId = ItemManager.getInstance().imageId;
        this.description = ItemManager.getInstance().description;
        this.speedBoost = ItemManager.getInstance().speedBoost;
        this.healAmount = ItemManager.getInstance().healAmount;
    }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description;}

    public int getType() { return type; }
    public void setType(int type) { this.type = type;}

    public float getSpeedBoost() {return speedBoost;}
    public void setSpeedBoost() { this.speedBoost = speedBoost;}

    public int getImageId() { return imageId; }
    public void setImageId() { this.imageId = imageId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getHealAmount() { return healAmount;}
    public void setHealAmount(int healAmount) { this.healAmount = healAmount; }
 }
