package com.dino.ncsu.dinorunner;

/**
 * Created by Kevin-Lenovo on 2/29/2016.
 */
public class Dinosaur {
    private String nameOfDino;
    private Integer imageId;

    public Dinosaur(String name, int id) {
        setNameOfDino(name);
        setImageId(id);
    }

    public String getNameOfDino() {
        return nameOfDino;
    }

    public void setNameOfDino(String nameOfDino) {
        this.nameOfDino = nameOfDino;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
