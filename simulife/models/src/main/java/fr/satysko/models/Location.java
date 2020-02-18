package fr.satysko.models;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.Objects;

@Embeddable
public class Location {

    private double posX;
    private double posY;
    @Transient
    private Vector2d pos;

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    @PostLoad
    private void postLoad(){
        pos = new Vector2d(posX, posY);
    }

    @PrePersist
    @PreUpdate
    private void preSave(){
        posX = pos.x;
        posY = pos.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location that = (Location) o;
        return pos.equals(that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
