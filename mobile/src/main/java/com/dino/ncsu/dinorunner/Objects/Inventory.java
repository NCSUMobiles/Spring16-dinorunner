package com.dino.ncsu.dinorunner.Objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiminfan on 4/13/2016.
 */
public class Inventory implements Serializable {
    private static Inventory instance;

    //Contains actual item objects
    private ArrayList<Item> consumableItems;
    private ArrayList<Item> equippableItems;
    private ArrayList<Item> trophyItems;

    //Used for reference.check for item existance ONLY
    private ArrayList<String> consumableItemsMap;
    private ArrayList<String> equippableItemsMap;
    private ArrayList<String> trophyItemsMap;



    public Inventory() {

    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public ArrayList<Item> getConsumableItems() { return consumableItems ;}
    public ArrayList<String> getConsumableItemsMap() { return consumableItemsMap; }

    public ArrayList<Item> getEquippableItems() { return equippableItems; }
    public ArrayList<String> getEquippableItemsMap() { return equippableItemsMap; }

    public ArrayList<Item> getTrophyItems() { return trophyItems; }
    public ArrayList<String> getTrophyItemsMap() { return trophyItemsMap; }



    public boolean addItem(Item item, int amount) {
        switch(item.getType()) {
            case 0: //Consumable
                if (consumableItemsMap.contains(item.getName())) {
                    int keyOfItem = consumableItemsMap.indexOf(item.getName());
                    item.setAmount(consumableItems.get(keyOfItem).getAmount() + amount);
                    consumableItems.set(keyOfItem, item);
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    consumableItems.add(item); //update item Inventory
                    consumableItemsMap.add(item.getName()); //update reference inventory
                    return true;
                }
            case 1: //Equippable
                if (equippableItemsMap.contains(item.getName())) {
                    int keyOfItem = equippableItemsMap.indexOf(item.getName());
                    item.setAmount(equippableItems.get(keyOfItem).getAmount() + amount);
                    equippableItems.set(keyOfItem, item);
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    equippableItems.add(item); //update item Inventory
                    equippableItemsMap.add(item.getName()); //update reference inventory
                    return true;
                }
            case 2: //Trophy
                if (trophyItemsMap.contains(item.getName())) {
                    int keyOfItem = trophyItemsMap.indexOf(item.getName());
                    item.setAmount(trophyItems.get(keyOfItem).getAmount() + amount);
                    trophyItems.set(keyOfItem, item);
                    return true;
                } else {
                    //Item does not exist in inventory, add to inventory
                    item.setAmount(amount);
                    trophyItems.add(item); //update item Inventory
                    trophyItemsMap.add(item.getName()); //update reference inventory
                    return true;
                }
        }
        Log.d("test", "We failed to add Item for some reason.");
        return false;
    }

    public boolean removeItem(Item item, int amount) {
        switch(item.getType()) {
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
                    consumableItems.set(keyOfItem, item);
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        consumableItems.remove(keyOfItem);
                        consumableItemsMap.remove(keyOfItem);
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
                    equippableItems.set(keyOfItem, item);
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        equippableItems.remove(keyOfItem);
                        equippableItemsMap.remove(keyOfItem);
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
                    trophyItems.set(keyOfItem, item);
                    //We removed all of the item, so we now remove item from inventory
                    if (item.getAmount() == 0) {
                        trophyItems.remove(keyOfItem);
                        trophyItemsMap.remove(keyOfItem);
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

}
