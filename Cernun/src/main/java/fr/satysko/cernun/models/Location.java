package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.Objects;

@Embeddable
public class Location {

    private int posX;
    private int posY;
    @Transient
    @JsonIgnore
    private Vector2d pos;

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

    public Vector2d getPos() {
        return pos;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
        this.posX = (int) pos.getX();
        this.posY = (int) pos.getY();
    }

    @PostLoad
    private void postLoad(){
        pos = new Vector2d(posX, posY);
    }

    @PrePersist
    @PreUpdate
    private void preSave(){
        posX = (int)pos.x;
        posY = (int)pos.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return posX == location.posX &&
                posY == location.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    @Override
    public String toString() {
        return "Location{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", pos=" + pos +
                '}';
    }
}
