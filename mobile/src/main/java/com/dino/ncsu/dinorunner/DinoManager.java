package com.dino.ncsu.dinorunner;

/**
 * Created by Jimin Fan on 4/11/2016.
 */
public class DinoManager {


    public void setDinos() {
        Dinosaur dino = Dinosaur.getInstance();
        String name = dino.getNameOfDino();

        switch(name) {
            case "Goblin":
                dino.setNameOfDino("Goblin");
                dino.setImageId(R.mipmap.goblin);
                dino.setAttack(5);
                dino.setMaxSpeed(1.5);
                dino.setSpeed(dino.getMaxSpeed());
                dino.setStunTime(5000);
                dino.setStunned(false);
                dino.setDistance(0);
                break;
        }
    }


}
