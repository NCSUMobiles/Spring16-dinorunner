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
    public int rarity; //0 - Common 1- Uncommon 2-Rare 3-Epic 4-Ascended 5-Legendary
    public String equipSlot; //NA, HEAD, SHOULDERS, CHEST, SHIRT, GLOVES, LEGS, SHOES, CAPE
    private int amount;
    private double sellAmount;
    private double buyAmount;

    private float speedBoost;
    private int healAmount;
    private float tempSpeedBoost;

    private boolean isVisible = true;
    private float[] resistances = new float[2]; //0-Dirt 1-Water
    private int consumeType; //0-NA 1-Food 2-Trap

    public Item (String name,int amount) {
        this.name = name;
        this.amount = amount;
        ItemManager.getInstance().setItemVariables(this);
    }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public float[] getResistances() { return resistances; }
    public void setResistances(float[] resistances) { this.resistances = resistances; }

    public int getConsumeType() { return consumeType; }
    public void setConsumeType(int consumeType) { this.consumeType = consumeType; }

    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description;}

    public int getType() { return type; }
    public void setType(int type) { this.type = type;}

    public float getSpeedBoost() {return speedBoost;}
    public void setSpeedBoost(float speedBoost) { this.speedBoost = speedBoost;}

    public int getImageId() { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getHealAmount() { return healAmount;}
    public void setHealAmount(int healAmount) { this.healAmount = healAmount; }

    public double getSellAmount() { return sellAmount;}
    public void setSellAmount(int sellAmount) { this.sellAmount = sellAmount;}

    public double getBuyAmount() { return buyAmount; }
    public void setBuyAmount(int buyAmount) { this.buyAmount = buyAmount; }

    public float getTempSpeedBoost() { return tempSpeedBoost; }
    public void setTempSpeedBoost(float tempSpeedBoost) { this.tempSpeedBoost = tempSpeedBoost; }

    public void setRarity(int rarity) { this.rarity = rarity; }
    public int getRarity() { return rarity; }
    
    public void setEquipSlot(String equipSlot) { this.equipSlot = equipSlot; }
    public String getEquipSlot() { return equipSlot; }

    public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
    public boolean getVisible() { return isVisible; }
 }
