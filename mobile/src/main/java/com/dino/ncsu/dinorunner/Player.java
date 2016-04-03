package com.dino.ncsu.dinorunner;

/**
 * Created by jiminfan on 2/28/2016.
 * <p>
 * Singleton Class that contains the necessary information that will pertain to the player
 */
public class Player {
    private double topSpeed; //Top speed achieved by player
    private double avgSpeed; //Average speed of player
    private double boostedSpeed;    //speed to boost player
    private double totalWeekDist;
    private double totalMonthDist;
    private EquippedItems listOfItems; //List of items for player
    private double distance; //Distance traveled
    private String playerName; //Player name

    private static Player instance; //instance of player

    public Player() {
        this.listOfItems = EquippedItems.getInstance();
    }

    /**
     * Constructor for the player
     *
     * @param topSpeed       The top speed of the player
     * @param avgSpeed       The average speed ran by the player
     * @param totalWeekDist  The distance traveled for the whole week
     * @param totalMonthDist The distance traveled for the whole month
     * @param listOfItems    The list of items earned through his/her lifetime
     */
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public EquippedItems getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(EquippedItems listOfItems) {
        this.listOfItems = listOfItems;
        initBoostedSpeed();
    }

    public void initBoostedSpeed() {
        this.boostedSpeed = 0;
        this.boostedSpeed += listOfItems.getHelmet().getSpeedBoost();
        this.boostedSpeed += listOfItems.getShirt().getSpeedBoost();
        this.boostedSpeed += listOfItems.getChest().getSpeedBoost();
        this.boostedSpeed += listOfItems.getPants().getSpeedBoost();
        this.boostedSpeed += listOfItems.getShoes().getSpeedBoost();
    }

    public double getBoostedSpeed() {
        return boostedSpeed;
    }

    public void setBoostedSpeed(double boostedSpeed) {
        this.boostedSpeed = boostedSpeed;
    }

    //Gets instance of player singleton
    public static Player getInstance() {
        return instance;
    }
}
