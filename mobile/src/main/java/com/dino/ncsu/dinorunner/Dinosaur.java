package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 2/29/2016.
 *
 * Singleton class that holds the essential data for the Monster class
 */
public class Dinosaur implements Serializable {
    private String nameOfDino; //Name of dinosaur
    private Integer imageId; //Image associated with dinosaur
    private double speed; //Dinosaur's current speed

    private double maxSpeed; //Dinosaur's original speed
    private double energy; //Dinosaur's energy level
    private double energyRegen; //Dinosaur's energy regen level
    private double distance; //Dinosaur's distance traveled so far
    private double attack; //Dinosaur's attack damage
    private double stunTime; //Dinosaur's time stunned after attacking player
    private boolean stunned; //is dinosaur stunned? If so, speed  = 0
    private double headStart; //How much headstart distance at beginning of race

    private static Dinosaur instance; //Singleton of Dinosaur

    public static Dinosaur getInstance() {

        if (instance == null) {
            instance = new Dinosaur();
        }
        return instance;
    }

    public Dinosaur() {

    }

    public String getNameOfDino() {
        return nameOfDino;
    }

    public void setNameOfDino(String nameOfDino) {
        this.nameOfDino = nameOfDino;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

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

}
