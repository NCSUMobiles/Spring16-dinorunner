package com.dino.ncsu.dinorunner.Managers;

import android.util.Log;

import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;

import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<String> defaultItemsMap = new ArrayList<String>(Arrays.asList("No Head Item",
                "No Shoulder Item",
                "No Chest Item",
                "No Shirt Item",
                "No Gloves Item",
                "No Leg Item",
                "No Feet Item",
                "No Cape Item"));

        Inventory.getInstance().setEquippedItemsMap(defaultItemsMap);
        ArrayList<Item> defaultItems = new ArrayList<Item>();
        for (int i = 0; i < defaultItemsMap.size(); i++) {
            Item item = new Item(defaultItemsMap.get(i), 1);
            ItemManager.getInstance().setItemVariables(item);
            defaultItems.add(item);
        }
        Inventory.getInstance().setEquippedItems(defaultItems);

    }

    public void setInventoryDefaults() {
        Inventory.getInstance().clearInventory();
    }

    public void setPreferences() {

    }
}
