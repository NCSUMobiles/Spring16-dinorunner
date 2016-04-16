package com.dino.ncsu.dinorunner.Objects;

/**
 * Created by Jimin Fan on 4/13/2016.
 */
public class Tile {

    private float x;
    private float y;
    private float dirX;
    private float dirY;
    String terrain;

    public Tile(float x, float y, String terrain, float dirX, float dirY) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.dirX = dirX;
        this.dirY = dirY;
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

    public void setDirX(float dirX) { this.dirX = dirX;}
    public float getDirX() { return dirX;}

    public void setDirY(float dirY) { this.dirY = dirY; }
    public float getDirY() { return dirY; }

}
