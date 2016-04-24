package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.Objects.Tile;

/**
 * Created by jiminfan on 4/11/2016.
 * Handles everything during the run
 */
public class RunManager {
    private static RunManager instance; //Instance of run manager
    private long lastStunnedTime;
    private long lastMoveTime;
    private Tile currentTile;

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

    public void checkStunMonster() {
        //Log.d("OK", "OK2!");
        long delta = System.currentTimeMillis() - lastStunnedTime;
        if (Dinosaur.getInstance().getStunned() && (Player.getInstance().getDistance() > Dinosaur.getInstance().getHeadStart())) {
           // Log.d("test", "We are stunned" + Dinosaur.getInstance().getStunned());
            Dinosaur.getInstance().setSpeed(0);

            if (delta > Dinosaur.getInstance().getStunTime()) {
                Dinosaur.getInstance().setStunned(false);
                Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
            }
        } else if (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
            Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
        } else {
            lastStunnedTime = System.currentTimeMillis();
        }
    }

    public void checkDistance() {
        //Log.d("OK", "OK3!");
        if ((getDistanceFromPlayer() <= 0)
                && !Dinosaur.getInstance().getStunned()
                && (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart())
                && (System.currentTimeMillis() - lastStunnedTime >= Dinosaur.getInstance().getStunTime())) {
            Dinosaur.getInstance().setStunned(true);
            lastStunnedTime = System.currentTimeMillis();
            //Log.d("attack", "We just got hit bro!");
            Player.getInstance().setHealth(Player.getInstance().getHealth() - Dinosaur.getInstance().getAttack());
        }
    }

    public double getHealth() {
        return Player.getInstance().getHealth();
    }

    public void updateDistance() {
        //Log.d("OK", "OK!");
        if (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
            //Log.d("Test", "Distance :" + Dinosaur.getInstance().getDistance());
            long delta = System.currentTimeMillis() - lastMoveTime;
            if (Dinosaur.getInstance().getDistance() == 0) {
                lastMoveTime = System.currentTimeMillis();
                if (delta > 1000) {
                    lastMoveTime = System.currentTimeMillis();
                }
            } else if ( (Player.getInstance().getDistance() - Dinosaur.getInstance().getDistance() > 0)
                    && ((!Dinosaur.getInstance().getStunned() && delta > 1000)
                    || (Dinosaur.getInstance().getStunned() && System.currentTimeMillis() - lastStunnedTime > Dinosaur.getInstance().getStunTime()))) {
                // Log.d("OK", "OK4!");
                lastMoveTime = System.currentTimeMillis();
            }
        }
    }

    public double getDinoSpeed() {
        return Dinosaur.getInstance().getSpeed();
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public static synchronized RunManager getInstance() {
        if (instance == null) {
            instance = new RunManager();
        }
        return instance;
    }


}
