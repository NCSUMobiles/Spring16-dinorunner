package com.dino.ncsu.dinorunner;

/**
 * Created by jiminfan on 2/28/2016.
 * <p>
 * Singleton Class that contains the necessary information that will pertain to the player
 */
public class Player {
    private double avgSpeed; //Average speed of player
    private double boostedStep;    //speed to boost player

    private double totalWeekDist;
    private double totalMonthDist;
    private EquippedItems listOfItems; //List of items for player
    private double distance; //Distance traveled
    private double stepsTraveled; //Steps traveled
    private String playerName; //Player name
    private double mStepLength = 30.48; //Player Step Length = .3048
    private double health = 100;
    private double totalStepLength; //Boosted step + Player step
    private boolean isMetric = true; //Default is True...more to come


    private static Player instance; //instance of player

    public Player() {
        this.listOfItems = EquippedItems.getInstance();
    }

    public double getAvgSpeed() { return avgSpeed; }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getTotalSpeed() { return avgSpeed + boostedStep; }

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getStepsTraveled() { return stepsTraveled; }

    public void setStepsTraveled(double steps) { this.stepsTraveled = steps; }

    public EquippedItems getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(EquippedItems listOfItems) {
        this.listOfItems = listOfItems;
        initBoostedSpeed();
    }

    public void initBoostedSpeed() {
        this.boostedStep = 0;
        this.boostedStep += listOfItems.getHelmet().getSpeedBoost();
        this.boostedStep += listOfItems.getShirt().getSpeedBoost();
        this.boostedStep += listOfItems.getChest().getSpeedBoost();
        this.boostedStep += listOfItems.getPants().getSpeedBoost();
        this.boostedStep += listOfItems.getShoes().getSpeedBoost();
    }

    public double getBoostedStep() {
        return boostedStep;
    }

    public void setBoostedStep(double boostedStep) {
        this.boostedStep = boostedStep;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setmStepLength(double mStepLength) {this.mStepLength = mStepLength;}

    public double getTotalStepLength() {
        totalStepLength = mStepLength + boostedStep;
        return totalStepLength;
    }

    public boolean getMetric() {return true;}


    public void setMetric(boolean isMetric) {this.isMetric = isMetric;}
    //Gets instance of player singleton
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }
}
