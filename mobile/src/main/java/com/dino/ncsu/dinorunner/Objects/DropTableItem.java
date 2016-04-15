package com.dino.ncsu.dinorunner.Objects;

/**
 * Created by jiminfan on 4/14/2016.
 */
public class DropTableItem {
    private String name;
    private int dropChance;
    private double maxAmount;
    private double minAmount;



    public DropTableItem(String name, double maxAmount, double minAmount, int dropChance) {
        this.name = name;
        this.dropChance = dropChance;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
    }

    public String getName() { return name; }
    public void setName() { this.name = name; }

    public int getDropChance() { return dropChance; }
    public void setDropChance(int dropChance) { this.dropChance = dropChance; }

    public double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(double maxAmount) { this.maxAmount = maxAmount; }

    public double getMinAmount() { return minAmount; }
    public void setMinAmount(double minAmount) { this.minAmount = minAmount; }
}
