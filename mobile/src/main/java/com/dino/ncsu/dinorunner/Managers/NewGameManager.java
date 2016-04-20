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
    }

    public void setEquipmentDefaults() {
        String[] defaultItemsMap = new String[] {"No Head Item",
                "No Shoulder Item",
                "No Chest Item",
                "No Shirt Item",
                "No Gloves Item",
                "No Leg Item",
                "No Feet Item",
                "No Cape Item"};

        Inventory.getInstance().setEquippedItemsMap(defaultItemsMap);
        Item[] defaultItems = new Item[defaultItemsMap.length];
        for (int i = 0; i < defaultItemsMap.length; i++) {
            Item item = new Item(defaultItemsMap[i], 1);
            ItemManager.getInstance().setItemVariables(item);
            defaultItems[i] = item;

        }
        Inventory.getInstance().setEquippedItems(defaultItems);

    }

    public void setInventoryDefaults() {
        Inventory.getInstance().clearInventory();
    }

    public void setPreferences() {

    }
}
