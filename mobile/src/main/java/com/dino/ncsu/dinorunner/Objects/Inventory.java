package com.dino.ncsu.dinorunner.Objects;

import android.util.Log;

import com.dino.ncsu.dinorunner.Managers.ItemManager;

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
    private ArrayList<Item> equippedItems = new ArrayList<>();

    //Used for reference.check for item existance ONLY
    private ArrayList<String> consumableItemsMap = new ArrayList<>();
    private ArrayList<String> equippableItemsMap = new ArrayList<>();
    private ArrayList<String> trophyItemsMap = new ArrayList<>();
    private ArrayList<String> equippedItemsMap = new ArrayList<>();
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

    public ArrayList<Item> getEquippedItems() {
        return equippedItems;
    }
    public void setEquippedItems(ArrayList<Item> equippedItems) { this.equippedItems = equippedItems; }

    public ArrayList<String> getEquippedItemsMap() {
        return equippedItemsMap;
    }
    public void setEquippedItemsMap(ArrayList<String> equippedItemsMap) { this.equippedItemsMap = equippedItemsMap; }

    public ArrayList<String> getDefaultItemsMap() {
        return defaultItemsMap;
    }

    public boolean addItem(String itemName, int amount) {
        Item item = new Item(itemName, amount);
        ItemManager.getInstance().setItemVariables(item);
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
                    synchronized (consumableItems) {
                        consumableItems.add(item); //update item Inventory
//                        Log.d("Line 73", "" + consumableItems.size());
                    }
                    synchronized (consumableItemsMap) {
                        consumableItemsMap.add(item.getName()); //update reference inventory
//                        Log.d("Line 77", "" + consumableItemsMap.size());
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
                    synchronized (equippableItems) {
                        equippableItems.add(item); //update item Inventory
//                        Log.d("Line 94", "" + equippableItems.size());
//                        Log.d("image", "" + equippableItems.get(equippableItems.size() - 1).getImageId());
                    }
                    synchronized (equippableItemsMap) {
                        equippableItemsMap.add(item.getName()); //update reference inventory
                        //Log.d("Line 98", "" + equippableItems.size());
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
                    synchronized (trophyItems) {
                        trophyItems.add(item); //update item Inventory
                    }
                    synchronized (trophyItemsMap) {
                        trophyItemsMap.add(item.getName()); //update reference inventory
                    }
                    return true;
                }
        }
        Log.d("test", "We failed to add Item for some reason.");
        return false;
    }

    public boolean removeItem(String itemName, int amount) {
        Item item = new Item(itemName, amount);
        ItemManager.getInstance().setItemVariables(item);
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
                        synchronized (consumableItems) {
                            consumableItems.remove(keyOfItem);
                        }
                        synchronized (consumableItemsMap) {
                            consumableItemsMap.remove(keyOfItem);
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
                        synchronized (equippableItems) {
                            equippableItems.remove(keyOfItem);
                        }
                        synchronized (equippableItemsMap) {
                            equippableItemsMap.remove(keyOfItem);
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
                        synchronized (trophyItems) {
                            trophyItems.remove(keyOfItem);
                        }
                        synchronized (trophyItemsMap) {
                            trophyItemsMap.remove(keyOfItem);
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

    public boolean equipItem(String itemName) {
        removeItem(itemName, 1);
        Item item = new Item(itemName, 1);

        ItemManager.getInstance().setItemVariables(item);
        //NA, HEAD, SHOULDERS, CHEST, SHIRT, GLOVES, PANTS, SHOES, CAPE
        switch (item.getEquipSlot()) {
            case "NA":
                addItem(itemName, 1);
                break;
            case "HEAD":
                if ((equippedItems.get(0) != null)) {
                    addItem(equippedItems.get(0).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(0, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "SHOULDERS":
                if (equippedItems.get(1) != null) {
                    addItem(equippedItems.get(1).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(1, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "CHEST":
                if (equippedItems.get(2) != null) {
                    addItem(equippedItems.get(2).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(2, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "SHIRT":
                if (equippedItems.get(3) != null) {
                    addItem(equippedItems.get(3).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(3, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "GLOVES":
                if (equippedItems.get(4) != null) {
                    addItem(equippedItems.get(4).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(4, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "LEGS":
                if (equippedItems.get(5) != null) {
                    addItem(equippedItems.get(5).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(5, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "FEET":
                if (equippedItems.get(6) != null) {
                    addItem(equippedItems.get(6).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(6, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
            case "CAPE":
                if (equippedItems.get(7) != null) {
                    addItem(equippedItems.get(7).getName(), 1);
                }
                synchronized (equippedItems) {
                    equippedItems.set(7, item);
                }
                Log.d("test", "We equipped " + item.getName() + " Successfully!");
                return true;
        }
        Log.d("test", "We successfully equiped Item");
        return false;
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
            equippedItems.clear();
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

        synchronized (equippedItemsMap) {
            equippedItemsMap.clear();
        }

        synchronized (defaultItemsMap) {
            defaultItemsMap.clear();
        }

        this.goldAmount = 0;
    }

}
