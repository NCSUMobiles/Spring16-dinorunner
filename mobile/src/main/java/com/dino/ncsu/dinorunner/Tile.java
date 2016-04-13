package com.dino.ncsu.dinorunner;

/**
 * Created by Jimin Fan on 4/13/2016.
 */
public class Tile {

    private float x;
    private float y;
    String terrain;

    public Tile(float x, float y, String terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }

    private void setX(float x) {
        this.x = x;
    }

    private float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getTerrain() {
        return terrain;
    }
}
