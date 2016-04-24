package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;

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
        Player.getInstance().setResistances(new float[]{1, .1f});
        Player.getInstance().setPlayerLevel(1);
    }

    public void setEquipmentDefaults() {

        String[] defaultItemsMap = new String[8];
        Item[] defaultItems = new Item[defaultItemsMap.length];
        Inventory.getInstance().setEquippedItems(defaultItems);

    }

    public void setInventoryDefaults() {
        Inventory.getInstance().clearInventory();
    }

    public void setPreferences() {

    }
}
