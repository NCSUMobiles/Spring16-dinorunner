package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.Player;

import java.util.ArrayList;

/**
 * Created by jiminfan on 4/11/2016.
 * Handles everything during the run
 */
public class RunManager {
    private static RunManager instance; //Instance of run manager
    private long lastStunnedTime;
    private long lastMoveTime;

    private ArrayList<Float> trapXPos = new ArrayList<Float>();
    private ArrayList<Float> trapYPos = new ArrayList<Float>();
    private ArrayList<String> trapType = new ArrayList<String>();
    private ArrayList<Integer> trapImage = new ArrayList<Integer>();

    public ArrayList<Integer> getTrapImage() {return trapImage;}
    public void setTrapImage(ArrayList<Integer> trapImage) {this.trapImage = trapImage;}

    public ArrayList<String> getTrapType() {return trapType;}
    public void setTrapType(ArrayList<String> trapType) { this.trapType = trapType; }

    public ArrayList<Float> getTrapYPos() {return trapYPos;}
    public void setTrapYPos(ArrayList<Float> trapYPos) {this.trapYPos = trapYPos;}

    public ArrayList<Float> getTrapXPos() {return trapXPos;}
    public void setTrapXPos(ArrayList<Float> trapXPos) {this.trapXPos = trapXPos;}

    public long getLastStunnedTime() {return lastStunnedTime; }
    public void setLastStunnedTime(long lastStunnedTime) {this.lastStunnedTime = lastStunnedTime; }

    private RunManager() {

    }

    public double getDistanceFromPlayer() {

        double distance = Player.getInstance().getDistance() - Dinosaur.getInstance().getDistance();
        //Log.d("OK", "" + distance);
        if (distance <= 0) {
            distance = 0;
        }
        return distance;
    }

    public float getPlayerTerrainModifier() {
        float[] resistances =  Player.getInstance().getResistances();
        switch(Player.getInstance().getCurrentTile().getTerrain()) {
            case "dirt":
               return resistances[0];
            case "water":
                return resistances[1];
        }
        return 1;
    }

    public float getPlayerResistanceMoidifer() {
        float[] resistances = Player.getInstance().getResistances();
        switch(Player.getInstance().getCurrentTile().getTerrain()) {
            case "dirt":
                return Player.getInstance().getResistances()[0] + Player.getInstance().getItemResistanceOfType(0);
            case "water":
                return Player.getInstance().getResistances()[1] + Player.getInstance().getItemResistanceOfType(1);
        }
        return 0;
    }

    public float getDinosaurTerrainModifier() {
        float[] resistances =  Dinosaur.getInstance().getResistances();
        switch(Dinosaur.getInstance().getCurrentTile().getTerrain()) {
            case "dirt":
                return resistances[0];
            case "water":
                return resistances[1];
        }
        return 1;
    }

    public void updateDistance() {
        synchronized (Dinosaur.getInstance())  {
            //Log.d("OK", "OK2!");
            long delta = System.currentTimeMillis() - lastStunnedTime;
            if (Dinosaur.getInstance().getStunned() && (Player.getInstance().getDistance() > Dinosaur.getInstance().getHeadStart())) {
                // Log.d("test", "We are stunned" + Dinosaur.getInstance().getStunned());
                Dinosaur.getInstance().setSpeed(0);

                if (delta > Dinosaur.getInstance().getStunTime()) {
                    Dinosaur.getInstance().setStunned(false);
                    Dinosaur.getInstance().setSpeed(currentDinoSpeed());
                }
            } else if (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
                Dinosaur.getInstance().setSpeed(currentDinoSpeed());
            } else {
                lastStunnedTime = System.currentTimeMillis();
            }
        }

    }

    public double currentDinoSpeed() {
        synchronized (Dinosaur.getInstance()) {
            double currSpeed = Dinosaur.getInstance().getMaxSpeed() * getDinosaurTerrainModifier();
            Dinosaur.getInstance().setSpeed(currSpeed);
            return currSpeed;
        }
    }

    public void checkDistance() {
        //Log.d("OK", "OK3!");

            if ((getDistanceFromPlayer() <= 0)
                    && !Dinosaur.getInstance().getStunned()
                    && (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart())
                    && (System.currentTimeMillis() - lastStunnedTime >= Dinosaur.getInstance().getStunTime())) {
                synchronized (Dinosaur.getInstance()) {
                    Dinosaur.getInstance().setStunned(true);
                }

                lastStunnedTime = System.currentTimeMillis();
                //Log.d("attack", "We just got hit bro!");
                synchronized (Player.getInstance()) {
                    Player.getInstance().setHealth(Player.getInstance().getHealth() - Dinosaur.getInstance().getAttack());
                }

        }
    }

    public void checkTerrainSound() {
        SoundManager.getInstance().playTerrainSound(Player.getInstance().getCurrentTile().getTerrain());
    }

    public static synchronized RunManager getInstance() {
        if (instance == null) {
            instance = new RunManager();
        }
        return instance;
    }

    public void clearTrapLists() {
        synchronized (trapType) {
            trapType.clear();
        }
        synchronized (trapImage) {
            trapImage.clear();
        }
        synchronized (trapXPos) {
            trapXPos.clear();
        }
        synchronized (trapYPos) {
            trapYPos.clear();
        }
    }

}
