package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.DropTableItem;

import java.util.ArrayList;

/**
 * Created by jiminfan on 4/14/2016.
 */
public class DropTableManager {

    public void setDropTable(String name) {
        ArrayList<DropTableItem> temp;
        //Sets drop table for each Dinosaur
        switch(name) {
            case "Goblin":
                Dinosaur.getInstance().clearTables();
                temp = Dinosaur.getInstance().getDropTable();
                temp.add(new DropTableItem("Fresh Apple", 1, 1, 1000));
                Dinosaur.getInstance().setDropTable(temp);
                break;
            case "Skeleton Spearman":
                Dinosaur.getInstance().clearTables();
                temp = Dinosaur.getInstance().getDropTable();
                temp.add(new DropTableItem("Copper Ore", 1, 1, 1000));
                Dinosaur.getInstance().setDropTable(temp);
                break;
        }
    }

}
