package fr.satysko.models;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.vecmath.Vector2d;

public class Food {
    private int worldID;
    private int energy;
    private boolean isMeat;
    private String picture;

    public Food() {}

    public Food(int worldID, int energy, boolean isMeat) {
        this.worldID = worldID;
        this.energy = energy;
        this.isMeat = isMeat;
        this.picture = "";
    }

    public int getWorldID() {
        return worldID;
    }

    public void setWorldID(int worldID) {
        this.worldID = worldID;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isMeat() {
        return isMeat;
    }

    public void setMeat(boolean meat) {
        isMeat = meat;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
