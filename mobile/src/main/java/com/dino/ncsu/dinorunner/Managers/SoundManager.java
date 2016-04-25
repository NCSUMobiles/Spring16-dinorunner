package com.dino.ncsu.dinorunner.Managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.dino.ncsu.dinorunner.R;

/**
 * Created by yaolu on 4/24/2016.
 */
public class SoundManager {
    private static SoundManager instance; //Instance of SoundManager
    private static Context context;
    private SoundPool pool;
    private SoundPool.Builder builder;

    private int dinoApproach;
    private int riverSound;
    private boolean isDinoApproach = false;

    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public static void setContext(Context c) {
        context = c;
    }

    public SoundManager() {
        pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);

        //load dino approach tick sound
        dinoApproach = pool.load(context, R.raw.ticking, 0);
        riverSound = pool.load(context,R.raw.realriver, 0);
        //wait for loading
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void playDinoApproach() {
        if(isDinoApproach == false) {
            isDinoApproach = true;
            pool.play(dinoApproach, 0.1f, 0.1f, 0, 0, 1);
            //System.out.println("========= Started playDinoApproach ===========" + RunManager.getInstance().getDistanceFromPlayer());
        }
    }

    public void stopDinoApproach() {
        if(isDinoApproach) {
            pool.stop(dinoApproach);
            isDinoApproach = false;
           // System.out.println("========= Stopped playDinoApproach ===========" + RunManager.getInstance().getDistanceFromPlayer());
        }
    }

    public void playTerrainSound(String terrain) {
        switch(terrain) {
            case "water":
                pool.play(riverSound, 0.1f, 0.1f, 0, 0, 1);
                break;
        }
    }
}
