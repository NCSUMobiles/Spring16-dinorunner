package com.dino.ncsu.dinorunner.Objects;

import android.util.Log;

import com.dino.ncsu.dinorunner.Managers.RunManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiminfan on 4/13/2016.
 */
public class Inventory implements Serializable {
    private static Inventory instance;

    //Contains actual item objects
    private ArrayList<Item> consumableItems = new ArrayList<>();
    private ArrayList<Item> equippableItems = new ArrayList<>();
    private ArrayList<Item> trophyItems = new ArrayList<>();
    private Item[] equippedItems = new Item[8];
    private String[] equippedConsumables = new String[4];

    //Used for reference.check for item existance ONLY
    private ArrayList<String> consumableItemsMap = new ArrayList<>();
    private ArrayList<String> equippableItemsMap = new ArrayList<>();
    private ArrayList<String> trophyItemsMap = new ArrayList<>();
    private ArrayList<String> defaultItemsMap = new ArrayList<>();

    private double goldAmount;

    public Inventory() {
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public ArrayList<Item> getConsumableItems() {
        return consumableItems;
    }
    public ArrayList<String> getConsumableItemsMap() {
        return consumableItemsMap;
    }

    public ArrayList<Item> getEquippableItems() {
        return equippableItems;
    }
    public ArrayList<String> getEquippableItemsMap() {
        return equippableItemsMap;
    }

    public ArrayList<Item> getTrophyItems() {
        return trophyItems;
    }
    public ArrayList<String> getTrophyItemsMap() {
        return trophyItemsMap;
    }

    public Item[] getEquippedItems() {
        return equippedItems;
    }
    public void setEquippedItems(Item[] equippedItemsTemp) {
        synchronized (equippedItems) {
            equippedItems = equippedItemsTemp;
        }
    }

    public String[] getEquippedConsumables() { return equippedConsumables; }
    public void setEquippedConsumables(String[] equippedConsumablesTemp) {
        synchronized (equippedConsumablesTemp) {
            equippedConsumables = equippedConsumablesTemp;
        }
    }

    public ArrayList<String> getDefaultItemsMap() {
        return defaultItemsMap;
    }

    public boolean addItem(String itemName, int amount) {
        Item item = new Item(itemName, amount);
        switch (item.getType()) {
            case 0: //Consumable
                if (consumableItemsMap.contains(item.getName())) {
                    int keyOfItem = consumableItemsMap.indexOf(item.getName());
                    item.setAmount(consumableItems.get(keyOfItem).getAmount() + amount);
                    synchronized (consumableItems) {
                        consumableItems.set(keyOfItem, item);
                    }
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    synchronized (consumableItemsMap) {
                        consumableItemsMap.add(item.getName()); //update reference inventory
//                        Log.d("Line 77", "" + consumableItemsMap.size());
                    }
                    synchronized (consumableItems) {
                        consumableItems.add(item); //update item Inventory
//                        Log.d("Line 73", "" + consumableItems.size());
                    }
                    return true;
                }
            case 1: //Equippable
                if (equippableItemsMap.contains(item.getName())) {
                    int keyOfItem = equippableItemsMap.indexOf(item.getName());
                    item.setAmount(equippableItems.get(keyOfItem).getAmount() + amount);
                    synchronized (equippableItems) {
                        equippableItems.set(keyOfItem, item);
                    }
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    synchronized (equippableItemsMap) {
                        equippableItemsMap.add(item.getName()); //update reference inventory
                        //Log.d("Line 98", "" + equippableItems.size());
                    }
                    synchronized (equippableItems) {
                        equippableItems.add(item); //update item Inventory
//                        Log.d("Line 94", "" + equippableItems.size());
//                        Log.d("image", "" + equippableItems.get(equippableItems.size() - 1).getImageId());
                    }
                    //Log.d("test", "We successfully added " + item.getName());
                    return true;
                }
            case 2: //Trophy
                if (trophyItemsMap.contains(item.getName())) {
                    int keyOfItem = trophyItemsMap.indexOf(item.getName());
                    item.setAmount(trophyItems.get(keyOfItem).getAmount() + amount);
                    synchronized (trophyItems) {
                        trophyItems.set(keyOfItem, item);
                    }
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    synchronized (trophyItemsMap) {
                        trophyItemsMap.add(item.getName()); //update reference inventory
                    }
                    synchronized (trophyItems) {
                        trophyItems.add(item); //update item Inventory
                    }
                    return true;
                }
        }
        Log.d("test", "We failed to add Item for some reason.");
        return false;
    }

    public boolean removeItem(String itemName, int amount) {
        Item item = new Item(itemName, amount);
        switch (item.getType()) {
            case 0: //Consumable
                if (consumableItemsMap.contains(item.getName())) {
                    int keyOfItem = consumableItemsMap.indexOf(item.getName());
                    //Item exists, but trying to remove more than what is there
                    if (consumableItems.get(keyOfItem).getAmount() < amount) {
                        Log.d("test", "Trying to remove more than amount that exists, cannot remove");
                        return false;
                    }
                    //Item exists, existing amount is higher than removing amount, remove
                    item.setAmount(consumableItems.get(keyOfItem).getAmount() - amount);
                    synchronized (consumableItems) {
                        consumableItems.set(keyOfItem, item);
                    }
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        synchronized (consumableItemsMap) {
                            consumableItemsMap.remove(keyOfItem);
                        }
                        synchronized (consumableItems) {
                            consumableItems.remove(keyOfItem);
                        }

                        Log.d("test", "Removed all Instances of " + item.getName() + "!");
                        return true;
                    }
                    Log.d("test", "Successfully Removed " + amount + " " + item.getName());
                    return true;
                } else {
                    //item does not exist, so you cannot delete
                    Log.d("test", "Item does not exist, cannot remove");
                    return false;
                }
            case 1: //Equippable
                if (equippableItemsMap.contains(item.getName())) {
                    int keyOfItem = equippableItemsMap.indexOf(item.getName());
                    //Item exists, but trying to remove more than what is there
                    if (equippableItems.get(keyOfItem).getAmount() < amount) {
                        Log.d("test", "Trying to remove more than amount that exists, cannot remove");
                        return false;
                    }
                    //Item exists, existing amount is higher than removing amount, remove
                    item.setAmount(equippableItems.get(keyOfItem).getAmount() - amount);
                    synchronized (equippableItems) {
                        equippableItems.set(keyOfItem, item);
                    }
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        synchronized (equippableItemsMap) {
                            equippableItemsMap.remove(keyOfItem);
                        }
                        synchronized (equippableItems) {
                            equippableItems.remove(keyOfItem);
                        }
                        Log.d("test", "Removed all Instances of " + item.getName() + "!");
                        return true;
                    }
                    Log.d("test", "Successfully Removed " + amount + " " + item.getName());
                    return true;
                } else {
                    //item does not exist, so you cannot delete
                    Log.d("test", "Item does not exist, cannot remove");
                    return false;
                }
            case 2: //Trophy
                if (trophyItemsMap.contains(item.getName())) {
                    int keyOfItem = trophyItemsMap.indexOf(item.getName());
                    //Item exists, but trying to remove more than what is there
                    if (trophyItems.get(keyOfItem).getAmount() < amount) {
                        Log.d("test", "Trying to remove more than amount that exists, cannot remove");
                        return false;
                    }
                    //Item exists, existing amount is higher than removing amount, remove
                    item.setAmount(trophyItems.get(keyOfItem).getAmount() - amount);
                    synchronized (trophyItems) {
                        trophyItems.set(keyOfItem, item);
                    }
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        synchronized (trophyItemsMap) {
                            trophyItemsMap.remove(keyOfItem);
                        }
                        synchronized (trophyItems) {
                            trophyItems.remove(keyOfItem);
                        }
                        Log.d("test", "Removed all Instances of " + item.getName() + "!");
                        return true;
                    }
                    Log.d("test", "Successfully Removed " + amount + " " + item.getName());
                    return true;
                } else {
                    //item does not exist, so you cannot delete
                    Log.d("test", "Item does not exist, cannot remove");
                    return false;
                }
        }
        Log.d("test", "We failed to add Item for some reason.");
        return false;
    }

    public Item inventoryContains(String itemName) {
        Item item = new Item(itemName, 1);
        switch (item.getType()) {
            case 0: //Consumable
                if (consumableItemsMap.contains(item.getName())) {
                    int keyOfItem = consumableItemsMap.indexOf(item.getName());
                    Item reference = consumableItems.get(keyOfItem);
                    Log.d("test", "Successfully returned " + itemName);
                    return reference;
                }
                Log.d("test", "NONONO " + itemName);
                return null;
            case 1: //Equippable
                if (equippableItemsMap.contains(item.getName())) {
                    int keyOfItem = equippableItemsMap.indexOf(item.getName());
                    Item reference = equippableItems.get(keyOfItem);
                    Log.d("test", "Successfully returned " + itemName);
                    return reference;
                }
                Log.d("test", "NONONO " + itemName);
                return null;
            case 2: //Trophy
                if (trophyItemsMap.contains(item.getName())) {
                    int keyOfItem = trophyItemsMap.indexOf(item.getName());
                    Item reference = trophyItems.get(keyOfItem);
                    Log.d("test", "Successfully returned " + itemName);
                    return reference;
                }
                Log.d("test", "NONONO " + itemName);
                return null;
        }
        Log.d("test", "NONONO " + itemName);
        return null;
    }

    public int inventoryItemAmountOf(String itemName) {
        Item item = new Item(itemName, 1);
        switch (item.getType()) {
            case 0: //Consumable
                if (consumableItemsMap.contains(item.getName())) {
                    int keyOfItem = consumableItemsMap.indexOf(item.getName());
                    Item reference = consumableItems.get(keyOfItem);
                    return reference.getAmount();
                }
                return 0;
            case 1: //Equippable
                if (equippableItemsMap.contains(item.getName())) {
                    int keyOfItem = equippableItemsMap.indexOf(item.getName());
                    Item reference = equippableItems.get(keyOfItem);
                    Log.d("test", "Successfully returned " + itemName);
                    return reference.getAmount();
                }
                return 0;
            case 2: //Trophy
                if (trophyItemsMap.contains(item.getName())) {
                    int keyOfItem = trophyItemsMap.indexOf(item.getName());
                    Item reference = trophyItems.get(keyOfItem);
                    Log.d("test", "Successfully returned " + itemName);
                    return reference.getAmount();
                }
                return 0;
        }
        return 0;
    }


    public int equipItem(String itemName) {
//        removeItem(itemName, 1);
        Item item = new Item(itemName, 1);
        Log.d("equipItemTagName", item.getName());
        Log.d("equipItemTagID", "" + item.getImageId());
        Log.d("equipItemTagSlot", item.getEquipSlot());
        //NA, HEAD, SHOULDERS, CHEST, SHIRT, GLOVES, PANTS, SHOES, CAPE
            switch (item.getEquipSlot()) {
                case "NA":
//                addItem(itemName, 1);
                    return -1;
                case "HEAD":
//                if ((equippedItems[0] != null)) {
//                    addItem(equippedItems[0].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[0] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 0;
                case "SHOULDERS":
//                if (equippedItems[1] != null) {
//                    addItem(equippedItems[1].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[1] = item;
                    }
                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 1;
                case "CHEST":
//                if (equippedItems[2] != null) {
//                    addItem(equippedItems[2].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[2] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 2;
                case "SHIRT":
//                if (equippedItems[3] != null) {
//                    addItem(equippedItems[3].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[3] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 3;
                case "GLOVES":
//                if (equippedItems[4] != null) {
//                    addItem(equippedItems[4].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[4] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 4;
                case "LEGS":
//                if (equippedItems[5] != null) {
//                    addItem(equippedItems[5].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[5] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 5;
                case "FEET":
//                if (equippedItems[6] != null) {
//                    addItem(equippedItems[6].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[6] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 6;
                case "CAPE":
//                if (equippedItems[7] != null) {
//                    addItem(equippedItems[7].getName(), 1);
//                }
                    synchronized (equippedItems) {
                        equippedItems[7] = item;
                    }

                    Log.d("test", "We equipped " + item.getName() + " Successfully!");
                    return 7;
            }
        Log.d("test", "We unsuccessfully equipped Item");
        return -1;
    }

    public void equipConsumableItem(int slot, String item) {
        synchronized (equippedConsumables) {
            equippedConsumables[slot] = item;
        }
    }

    public void useConsumableItem(String item) {
        Item consumeItem = new Item(item, 1);
        switch(consumeItem.getConsumeType()) {
            case "NA":
                Log.d("Test ", "Can't eat a nonconsumable item...Error");
                break;
            case "FOOD":
                Log.d("Test ", "We ate the " + item + " " + inventoryContains(item).getAmount() + " left");

                synchronized (Player.getInstance()) {
                    Player.getInstance().setHealth(Player.getInstance().getHealth() + consumeItem.getHealAmount());
                }
                break;
            case "TRAP":
                Log.d("Test ", "We used the " + item + " " + inventoryContains(item).getAmount() + " left");
                synchronized (RunManager.getInstance()) {
                    ArrayList<String> trapType = RunManager.getInstance().getTrapType();
                    ArrayList<Float> trapXPos = RunManager.getInstance().getTrapXPos();
                    ArrayList<Float> trapYPos = RunManager.getInstance().getTrapYPos();
                    ArrayList<Integer> trackImage = RunManager.getInstance().getTrapImage();

                    trapType.add(item);
                    trapXPos.add(Player.getInstance().getCurrentTilePosX());
                    trapYPos.add(Player.getInstance().getCurrentTilePosY());

                    Item tempItem = new Item(item, 1);
                    trackImage.add(tempItem.getImageId());

                    RunManager.getInstance().setTrapType(trapType);
                    RunManager.getInstance().setTrapXPos(trapXPos);
                    RunManager.getInstance().setTrapYPos(trapYPos);
                    RunManager.getInstance().setTrapImage(trackImage);
                }
                break;
        }
        removeItem(item, 1);
    }

    public double getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(double goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void clearInventory() {
        synchronized (consumableItems) {
            consumableItems.clear();
        }

        synchronized (equippableItems) {
            equippableItems.clear();
        }
        synchronized (trophyItems) {

            trophyItems.clear();
        }

        synchronized(equippedItems) {
            equippedItems = new Item[8];
        }

        synchronized (consumableItemsMap) {
            consumableItemsMap.clear();
        }
        synchronized (equippableItemsMap) {
            equippableItemsMap.clear();
        }

        synchronized (trophyItemsMap) {
            trophyItemsMap.clear();
        }

        synchronized (defaultItemsMap) {
            defaultItemsMap.clear();
        }

        this.goldAmount = 0;
    }

}
