package com.dino.ncsu.dinorunner;

/**
 * Created by jiminfan on 2/28/2016.
 */
public class Player {
    private double topSpeed;
    private double avgSpeed;
    private double totalWeekDist;
    private double totalMonthDist;

    public Player() {
    }

    public Player(double topSpeed, double avgSpeed, double totalWeekDist, double totalMonthDist) {
        setTopSpeed(topSpeed);
        setAvgSpeed(avgSpeed);
        setTotalWeekDist(totalWeekDist);
        setTotalMonthDist(totalMonthDist);
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getTotalWeekDist() {
        return totalWeekDist;
    }

    public void setTotalWeekDist(double totalWeekDist) {
        this.totalWeekDist = totalWeekDist;
    }

    public double getTotalMonthDist() {
        return totalMonthDist;
    }

    public void setTotalMonthDist(double totalMonthDist) {
        this.totalMonthDist = totalMonthDist;
    }
}
