package com.dino.ncsu.dinorunner;

/**
 * Created by Kevin-Lenovo on 2/29/2016.
 */
public class Dinosaur {
    private String nameOfDino; //Name of dinosaur
    private Integer imageId; //Image associated with dinosaur
    private double currSpeed; //Dinosaur's current speed
    private double maxSpeed; //Dinosaur's maximum speed
    private double acceleration; //Dinosaur's acceleration
    private double energy; //Dinosaur's energy level
    private double energyRegen; //Dinosaur's energy regen level
    private double distance; //Dinosaur's distance traveled so far

    //Dinosaur object
    public Dinosaur(String name, int id, double curr, double max, double acc, double engy, double engyRegen) {
        setNameOfDino(name);
        setImageId(id);
        setCurrSpeed(curr);
        setMaxSpeed(max);
        setAcceleration(acc);
        setEnergy(engy);
        setEnergyRegen(engyRegen);

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

    public void setCurrSpeed(double currSpeed) {
        this.currSpeed = currSpeed;
    }

    public double getCurrSpeed() {
        return currSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getAcceleration() {
        return acceleration;
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


}
