package fr.satysko.cernun.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Food extends Entite {
    @ManyToOne
    private World world;
    private int energy;
    private boolean isMeat;
    @OneToOne
    private Picture picture;
    @Embedded
    private Location location;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
