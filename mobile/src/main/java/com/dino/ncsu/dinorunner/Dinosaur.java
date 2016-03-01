package com.dino.ncsu.dinorunner;

/**
 * Created by Kevin-Lenovo on 2/29/2016.
 */
public class Dinosaur {
    private String nameOfDino; //Name of dinosaur
    private Integer imageId; //Image associated with dinosaur
    private Float currSpeed; //Dinosaur's current speed
    private Float maxSpeed; //Dinosaur's maximum speed
    private Float acceleration; //Dinosaur's acceleration
    private Float energy; //Dinosaur's energy level
    private Float energyRegen; //Dinosaur's energy regen level
    private Float distance; //Dinosaur's distance traveled so far
    private Float distanceFromPlayer; //Dinosaur's distance from player

    //Dinosaur object
    public Dinosaur(String name, int id, float curr, float max, float acc, float engy, float engyRegen) {
        setNameOfDino(name);
        setImageId(id);
        setCurrSpeed(curr);
        setMaxSpeed(max);
        setAcceleration(acc);
        setEnergy(engy);
        setEnergyRegen(engyRegen);

    }

    public String getNameOfDino() { return nameOfDino; }

    public void setNameOfDino(String nameOfDino) { this.nameOfDino = nameOfDino; }

    public Integer getImageId() { return imageId; }

    public void setImageId(Integer imageId) { this.imageId = imageId; }

    public void setCurrSpeed(Float currSpeed) { this.currSpeed = currSpeed; }

    public Float getCurrSpeed() { return currSpeed; }

    public void setMaxSpeed(Float maxSpeed) { this.maxSpeed = maxSpeed; }

    public Float getMaxSpeed() { return maxSpeed; }

    public void setAcceleration(Float acceleration) { this.acceleration = acceleration; }

    public Float getAcceleration() { return acceleration; }

    public void setEnergy(Float energy) { this.energy = energy; }

    public Float getEnergy() { return energy; }

    public void setEnergyRegen(Float energyRegen) { this.energyRegen = energyRegen; }

    public Float getEnergyRegen() { return energyRegen; }

    public void setDistance(Float distance) { this.distance = distance; }

    public Float getDistance() { return distance; }

    
}
