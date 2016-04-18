package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;

import java.util.ArrayList;

/**
 * Created by Jimin Fan on 4/18/2016.
 */
public class NewGameManager {
    private static NewGameManager instance; //Instance of Item manager

    public static synchronized NewGameManager getInstance() {
        if (instance == null) {
            instance = new NewGameManager();
        }
        return instance;
    }

    public void setDefaults() {
        setPlayerDefaults();
        setInventoryDefaults();
        setEquipmentDefaults();

    }

    public void setPlayerDefaults() {
        Player.getInstance().setExperience(0);
        Player.getInstance().setMaxHealth(100);
        Player.getInstance().setmStepLength(30);
        Player.getInstance().setMetric(true);
    }

    public void setEquipmentDefaults() {
        String[] defaultItems = {"No Head Item",
                "No Chest Item",
                "No Shirt Item",
                "No Leg Item",
                "No Feet Item"};

        for (int i = 0; i < defaultItems.length; i++) {
            Inventory.getInstance().equipItem(defaultItems[i]);
        }

    }

    public void setInventoryDefaults() {
        Inventory.getInstance().clearInventory();
    }

    public void setPreferences() {

    }
}
