package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.R;

/**
 * Created by Jimin Fan on 4/11/2016.
 */
public class DinoManager {


    public void setDinos() {
        String name = Dinosaur.getInstance().getNameOfDino();
        DropTableManager dropTableManager = new DropTableManager();

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
                dropTableManager.setDropTable(Dinosaur.getInstance().getNameOfDino());
                break;
            case "Skeleton Spearman":
                Dinosaur.getInstance().setNameOfDino("Skeleton Spearman");
                Dinosaur.getInstance().setHeadStart(10);
                Dinosaur.getInstance().setImageId(R.mipmap.skele_spearman);
                Dinosaur.getInstance().setAttack(20);
                Dinosaur.getInstance().setMaxSpeed(.2);
                Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
                Dinosaur.getInstance().setStunTime(7000);
                Dinosaur.getInstance().setStunned(false);
                Dinosaur.getInstance().setDistance(0);
                break;
            case "Forest Giant":
                Dinosaur.getInstance().setNameOfDino("Forest Giant");
                Dinosaur.getInstance().setHeadStart(20);
                Dinosaur.getInstance().setImageId(R.mipmap.forest_giant);
                Dinosaur.getInstance().setAttack(35);
                Dinosaur.getInstance().setMaxSpeed(.3);
                Dinosaur.getInstance().setSpeed(Dinosaur.getInstance().getMaxSpeed());
                Dinosaur.getInstance().setStunTime(10000);
                Dinosaur.getInstance().setStunned(false);
                Dinosaur.getInstance().setDistance(0);
                break;
        }
    }


}
