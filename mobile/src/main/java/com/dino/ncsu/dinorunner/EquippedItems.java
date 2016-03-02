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

    public EquippedItems() {
        this.helmet = new RunningItem();
        this.chest = new RunningItem();
        this.shirt = new RunningItem();
        this.pants = new RunningItem();
        this.shoes = new RunningItem();
    }

    public EquippedItems(RunningItem helmet, RunningItem chest, RunningItem shirt, RunningItem pants, RunningItem shoes) {
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
}
