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

    public double currentDinoSpeed() {
        double currSpeed = Dinosaur.getInstance().getMaxSpeed() * getDinosaurTerrainModifier();
        Dinosaur.getInstance().setSpeed(currSpeed);
        return currSpeed;
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

    public static synchronized RunManager getInstance() {
        if (instance == null) {
            instance = new RunManager();
        }
        return instance;
    }


}
