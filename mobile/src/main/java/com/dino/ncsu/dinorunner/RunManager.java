package com.dino.ncsu.dinorunner;

import android.util.Log;

/**
 * Created by jiminfan on 4/11/2016.
 * Handles everything during the run
 */
public class RunManager {
    private static RunManager instance; //Instance of run manager
    private double distanceFromPlayer; //Distance monster from player
    private long lastStunnedTime;
    private long lastMoveTime;

    private RunManager() {

    }

    public double getDistanceFromPlayer() {

        double distance = Player.getInstance().getDistance() - Dinosaur.getInstance().getDistance();
        Log.d("OK", "" + distance );
        if (distance < 0) {
            distance = 0;
        }
        return distance;
    }

    public void checkStunMonster() {
        //Log.d("OK", "OK2!");
        long delta = System.currentTimeMillis() - lastStunnedTime;
        if (Dinosaur.getInstance().getStunned()) {
            Log.d("test", "We are stunned" + Dinosaur.getInstance().getStunned());
            Dinosaur.getInstance().setSpeed(0);

            if (delta > Dinosaur.getInstance().getStunTime()) {
                lastStunnedTime = System.currentTimeMillis();
                Dinosaur.getInstance().setStunned(false);
                Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
            }
        }
        else {
            Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
        }
    }

    public void checkDistance() {
        //Log.d("OK", "OK3!");
        if (getDistanceFromPlayer() <= 0 && Player.getInstance().getDistance() > 10) {
            Dinosaur.getInstance().setStunned(true);
            Player.getInstance().setHealth(Player.getInstance().getHealth() - Dinosaur.getInstance().getAttack());
        }
    }

    public double getHealth() {
        return Player.getInstance().getHealth();
    }

    public void updateDistance() {
        //Log.d("OK", "OK!");
        Log.d("Test", "Distance :" + Dinosaur.getInstance().getDistance());
        long delta = System.currentTimeMillis() - lastMoveTime;
//        if (dino.getDistance() == 0) {
//            lastMoveTime = System.currentTimeMillis();
//            if (delta > dino.getStunTime()) {
//                lastMoveTime = System.currentTimeMillis();
//                dino.setDistance(dino.getDistance() + dino.getSpeed());
//            }
//        }
//        else
            if (delta > 1000) {
           // Log.d("OK", "OK4!");
            lastMoveTime = System.currentTimeMillis();
            Dinosaur.getInstance().setDistance(Dinosaur.getInstance().getDistance() + Dinosaur.getInstance().getSpeed());
        }
    }

    public double getDinoSpeed() {
        return Dinosaur.getInstance().getSpeed();
    }

    public static synchronized RunManager getInstance() {
        if (instance == null) {
            instance = new RunManager();
        }
        return instance;
    }
}
