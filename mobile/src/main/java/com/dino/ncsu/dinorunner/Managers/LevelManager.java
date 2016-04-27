package com.dino.ncsu.dinorunner.Managers;

import android.util.Log;

/**
 * Created by Jimin Fan on 4/23/2016.
 */
public class LevelManager {
    private int[] levels = new int[100];
    private double[] expUntilNext = new double[99];
    private double[] experience = new double[100];


    public LevelManager() {
        for (int i = 1; i < 100; i++) {
            levels[i] = i;
            if (i >= 2) {
                experience[i] = experience[i-1] + 50 * Math.pow(i, 1.1);
            }
            else {
                experience[i] = 50 * Math.pow(i, 1.1);
            }

        }
        for (int i = 1; i < 99; i++) {
            expUntilNext[i] = experience[i + 1] - experience[i];
        }
    }

    public int convertExpToLevel(int xp) {
        if (xp >= experience[experience.length-1]) {
            return levels[experience.length-1];
        }
        int count = 1;
        while(xp >= experience[count]) {
            count++;
        }
        return count;
    }

    public double convertLevelToExpTillNext(int currLevel) {
        if (currLevel >= 99) {
            return 0;
        }
        return expUntilNext[currLevel];
    }

}
