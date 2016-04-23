package com.dino.ncsu.dinorunner.Managers;

/**
 * Created by Jimin Fan on 4/23/2016.
 */
public class LevelManager {
    private int[] levels = new int[99];
    private double[] expUntilNext = new double[99];
    private double[] experience = new double[98];


    public LevelManager() {
        for (int i = 1; i < 100; i++) {
            levels[i] = i;
            experience[i] = 50 * Math.pow(i, 1.1);
        }
        for (int i = 1; i < 99; i++) {
            expUntilNext[i] = experience[i + 1] - experience[i];
        }
    }

    public int convertExpToLevel(int xp) {
        if (xp >= experience[experience.length]) {
            return levels[experience.length];
        }
        int count = 1;
        while(xp >= experience[count]) {
            count++;
        }
        return count - 1;
    }

    public double convertLevelToExpTillNext(int currLevel) {
        if (currLevel >= 99) {
            return 0;
        }
        return expUntilNext[currLevel];
    }

}
