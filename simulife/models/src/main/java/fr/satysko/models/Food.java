package fr.satysko.models;

public class Food {
    private int energy;
    private boolean isMeat;
    private String picture;

    public Food() {}

    public Food(int energy, boolean isMeat) {
        this.energy = energy;
        this.isMeat = isMeat;
        this.picture = null;
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
