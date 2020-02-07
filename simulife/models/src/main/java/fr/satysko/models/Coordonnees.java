package fr.satysko.models;

import javax.persistence.*;
import javax.vecmath.Vector2d;

@Embeddable
public class Coordonnees {

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
}
