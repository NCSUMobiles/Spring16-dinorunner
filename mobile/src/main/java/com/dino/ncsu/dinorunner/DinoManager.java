package com.dino.ncsu.dinorunner;

import android.util.Log;

/**
 * Created by Jimin Fan on 4/11/2016.
 */
public class DinoManager {


    public void setDinos() {
        String name = Dinosaur.getInstance().getNameOfDino();

        switch(name) {
            case "Goblin":
                Dinosaur.getInstance().setNameOfDino("Goblin");
                Dinosaur.getInstance().setHeadStart(10);
                Dinosaur.getInstance().setImageId(R.mipmap.goblin);
                Dinosaur.getInstance().setAttack(5);
                Dinosaur.getInstance().setMaxSpeed(.1);
                Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
                Dinosaur.getInstance().setStunTime(5000);
                Dinosaur.getInstance().setStunned(false);
                Dinosaur.getInstance().setDistance(0);

                Log.d("StunTIme", "" + Dinosaur.getInstance().getHeadStart());
                break;
        }
    }


}
