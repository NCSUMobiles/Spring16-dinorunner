package com.dino.ncsu.dinorunner.Objects;

import com.dino.ncsu.dinorunner.Managers.NewGameManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiminfan on 2/28/2016.
 * <p/>
 * Singleton Class that contains the necessary information that will pertain to the player
 */
public class Player implements Serializable {
    private double avgSpeed; //Average speed of player
    private double boostedStep;    //speed to boost player

    private double totalWeekDist;
    private double totalMonthDist;
    private ArrayList<Item> listOfItems; //List of items for player
    private double distance; //Distance traveled
    private double stepsTraveled; //Steps traveled
    private int currentTile; //Current tile of track player is on
    private String playerName; //Player name
    private double mStepLength = 30.48; //Player Step Length = .3048
    private double maxHealth = 100;
    private double health = maxHealth;
    private int experience;
    private double totalStepLength; //Boosted step + Player step
    private boolean isMetric = true; //Default is True...more to come
    private boolean isNewGame = true;


    private static Player instance; //instance of player

    public Player() {
        this.listOfItems = Inventory.getInstance().getEquippableItems();
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getTotalSpeed() {
        return avgSpeed + boostedStep;
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

    public double getStepsTraveled() {
        return stepsTraveled;
    }

    public void setStepsTraveled(double steps) {
        this.stepsTraveled = steps;
    }

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(ArrayList<Item> listOfItems) {
        this.listOfItems = listOfItems;
        initBoostedSpeed();
    }

    public void initBoostedSpeed() {
        this.boostedStep = 0;
        for (int j = 0; j < 8; j++)
            this.boostedStep += listOfItems.get(j).getSpeedBoost();
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

    public void setmStepLength(double mStepLength) {
        this.mStepLength = mStepLength;
    }

    public double getTotalStepLength() {
        totalStepLength = mStepLength + boostedStep;
        return totalStepLength;
    }

    public boolean getMetric() {
        return true;
    }


    public void setMetric(boolean isMetric) {
        this.isMetric = isMetric;
    }

    //Gets instance of player singleton
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(int currentTile) {
        this.currentTile = currentTile;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean getIsNewGame() {
        return isNewGame;
    }

    public void setIsNewGame(boolean isNewGame) {
        this.isNewGame = isNewGame;
    }

    public void checkNewGame() {
        if (this.isNewGame) {
            Inventory.getInstance().clearInventory();
            NewGameManager.getInstance().setDefaults();
            this.isNewGame = false;
        }
    }
}
