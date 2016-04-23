package com.dino.ncsu.dinorunner.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kevin-Lenovo on 2/29/2016.
 *
 * Singleton class that holds the essential data for the Monster class
 */
public class Dinosaur implements Serializable {
    private String nameOfDino; //Name of dinosaur
    private Integer imageId; //Image associated with dinosaur
    private Integer galleryId; //Gallery Image associated with dinosaur
    private Integer spriteSheetId; //Sprite sheet for animating dinosaur
    private Integer frameCount; //Number of frames for image
    private double speed; //Dinosaur's current speed

    private double maxSpeed; //Dinosaur's original speed
    private double energy; //Dinosaur's energy level
    private double energyRegen; //Dinosaur's energy regen level
    private double distance; //Dinosaur's distance traveled so far
    private double attack; //Dinosaur's attack damage
    private double stunTime; //Dinosaur's time stunned after attacking player
    private boolean stunned; //is dinosaur stunned? If so, speed  = 0
    private double headStart; //How much headstart distance at beginning of race
//    private ArrayList<Integer> dropChance; //Table of drops, where index = itemENUM, value = chance / 100
//    private ArrayList<Item> dropTable; //Table of drops, where index = itemENUM, value = Item object (includes amount)
    private ArrayList<DropTableItem> dropTable;
    private double minGold;
    private double maxGold;
    private int experience;


    private static Dinosaur instance; //Singleton of Dinosaur

    public static Dinosaur getInstance() {

        if (instance == null) {
            instance = new Dinosaur();
        }
        return instance;
    }

    public Dinosaur() {
//        this.dropChance = new ArrayList<Integer>();
//        this.dropTable = new ArrayList<Item>();
        this.dropTable = new ArrayList<DropTableItem>();
    }

    public void clearTables() {
        synchronized (dropTable) {
            dropTable.clear();
        }
    }

    public String getNameOfDino() { return nameOfDino; }
    public void setNameOfDino(String nameOfDino) {
        this.nameOfDino = nameOfDino;
    }

    public Integer getImageId() {
        return imageId;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getGalleryId() { return galleryId; }
    public void setGalleryId(Integer galleryId) { this.galleryId = galleryId;}

    public Integer getSpriteSheetId() { return spriteSheetId; }
    public void setSpriteSheetId(int spriteSheetId) { this.spriteSheetId = spriteSheetId; }

    public Integer getFrameCount() { return frameCount; }
    public void setFrameCount(Integer frameCount) { this.frameCount = frameCount;}

    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public double getEnergy() {
        return energy;
    }

    public void setEnergyRegen(double energyRegen) {
        this.energyRegen = energyRegen;
    }
    public double getEnergyRegen() {
        return energyRegen;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getDistance() {
        return distance;
    }

    public void setAttack(double attack) { this.attack = attack;}
    public double getAttack() { return attack; }

    public void setStunTime(double stunTime) { this.stunTime = stunTime;}
    public double getStunTime() { return stunTime;}

    public void setSpeed(double speed) { this.speed = speed; }
    public double getSpeed() { return speed; }

    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }
    public double getMaxSpeed() { return maxSpeed; }

    public void setStunned(boolean stunned) { this.stunned = stunned;}
    public boolean getStunned() { return stunned; }

    public void setHeadStart(double headStart) { this.headStart = headStart; }
    public double getHeadStart() { return headStart; }

//    public void setDropChance(ArrayList<Integer> dropChance) { this.dropChance = dropChance;}
//    public ArrayList<Integer> getDropChance() { return dropChance; }
//
//    public void setDropTable(ArrayList<Item> dropTable) { this.dropTable = dropTable; }
//    public ArrayList<Item> getDropTable() { return dropTable; }

    public void setDropTable(ArrayList<DropTableItem> dropTable) { this.dropTable = dropTable; }
    public ArrayList<DropTableItem> getDropTable() { return dropTable; }

    public void setMinGold(double minGold) { this.minGold = minGold; }
    public double getMinGold() { return minGold; }

    public void setMaxGold(double maxGold) { this.maxGold = maxGold; }
    public double getMaxGold() { return maxGold; }

    public void setExperience(int experience) { this.experience = experience; }
    public int getExperience() { return experience; }

}
