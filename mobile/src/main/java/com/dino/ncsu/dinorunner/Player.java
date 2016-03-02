package com.dino.ncsu.dinorunner;

/**
 * Created by jiminfan on 2/28/2016.
 */
public class Player {
    private double topSpeed; //Top speed achieved by player
    private double avgSpeed; //Average speed of player
    private double totalWeekDist;
    private double totalMonthDist;
    private EquippedItems listOfItems; //List of items for player
    private double distance; //Distance traveled
    private String playerName; //Player name


    public Player() {
        this.listOfItems = new EquippedItems();
    }

    public Player(double topSpeed, double avgSpeed, double totalWeekDist, double totalMonthDist, EquippedItems listOfItems) {
        this.topSpeed = topSpeed;
        this.avgSpeed = avgSpeed;
        this.totalWeekDist = totalWeekDist;
        this.totalMonthDist = totalMonthDist;
        this.listOfItems = listOfItems;
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
