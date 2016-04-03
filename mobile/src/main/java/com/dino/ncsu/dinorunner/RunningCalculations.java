package com.dino.ncsu.dinorunner;

/**
 * Created by Kevin-Lenovo on 3/31/2016.
 * <p>
 * Holds the running calculations for the player given from the pedometer
 */
public class RunningCalculations {
    //Private variables in this class
    private int distanceTraveled;
    private float speed;

    /**
     * Constructor to initialize the player speed and distance
     */
    public RunningCalculations() {
        this.speed = 0;
        this.distanceTraveled = 0;
    }

    //Returns player speed
    public float getPlayerSpeed() {
        return this.speed;
    }

    //Returns distance traveled
    public int getDistanceTraveled() {
        return this.distanceTraveled;
    }

    public void setDistanceTraveled(int distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void addStep() {
        this.distanceTraveled++;
    }
}
