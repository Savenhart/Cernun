package fr.satysko.models;

public class Food {
    private int worldID;
    private int posX;
    private int posY;
    private int energy;
    private boolean isMeat;
    private String picture;

    public Food() {}

    public Food(int worldID, int posX, int posY, int energy, boolean isMeat) {
        this.worldID = worldID;
        this.posX = posX;
        this.posY = posY;
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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
