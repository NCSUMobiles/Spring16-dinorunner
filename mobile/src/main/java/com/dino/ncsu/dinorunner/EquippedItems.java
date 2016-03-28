package com.dino.ncsu.dinorunner;

import java.io.Serializable;

/**
 * Created by Kevin-Lenovo on 3/1/2016.
 */
public class EquippedItems implements Serializable {

    private RunningItem helmet;
    private RunningItem chest;
    private RunningItem shirt;
    private RunningItem pants;
    private RunningItem shoes;

    private static EquippedItems instance; //Singleton Instance

    static {
        instance = new EquippedItems();
    }

    public static EquippedItems getInstance() {
        return instance;
    }

    private  EquippedItems() {
        this.helmet = new RunningItem();
        this.chest = new RunningItem();
        this.shirt = new RunningItem();
        this.pants = new RunningItem();
        this.shoes = new RunningItem();
    }

    private EquippedItems(RunningItem helmet, RunningItem chest, RunningItem shirt, RunningItem pants, RunningItem shoes) {
        setHelmet(helmet);
        setChest(chest);
        setShirt(shirt);
        setPants(pants);
        setShoes(shoes);
    }

    public RunningItem getHelmet() {
        return helmet;
    }

    public void setHelmet(RunningItem helmet) {
        this.helmet = helmet;
    }

    public RunningItem getChest() {
        return chest;
    }

    public void setChest(RunningItem chest) {
        this.chest = chest;
    }

    public RunningItem getShirt() {
        return shirt;
    }

    public void setShirt(RunningItem shirt) {
        this.shirt = shirt;
    }

    public RunningItem getPants() {
        return pants;
    }

    public void setPants(RunningItem pants) {
        this.pants = pants;
    }

    public RunningItem getShoes() {
        return shoes;
    }

    public void setShoes(RunningItem shoes) {
        this.shoes = shoes;
    }

    public void setItemAtIndex(int i, RunningItem item) {
        switch(i) {
            case 0:
                setHelmet(item);
                break;
            case 1:
                setChest(item);
                break;
            case 2:
                setShirt(item);
                break;
            case 3:
                setPants(item);
                break;
            case 4:
                setShoes(item);
                break;
        }
    }
}
