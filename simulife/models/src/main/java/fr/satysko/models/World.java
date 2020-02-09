package fr.satysko.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.satysko.utils.OpenSimplexNoise;

import javax.persistence.*;
import javax.vecmath.Vector2d;

@Entity
class World extends Entite{

    String name;
    long seed;

    @Transient
    Map<Coordonnees, Cell> cells;
    @OneToMany
    Set<Cell> cellsSet;

    @Transient
    Map<Coordonnees, Food> foods;
    @OneToMany
    Set<Food> foodsSet;

    @Transient
    OpenSimplexNoise oNoise;

    public World(){}

    public World(String n, long s) {
        name = n;
        seed = s;
        oNoise = new OpenSimplexNoise(seed);
        cells = new HashMap<Coordonnees, Cell>();
    }

    public void genCell(int x, int y) {
        Coordonnees k = new Coordonnees();
        k.setPos(new Vector2d(x, y));
        float niv = (float) oNoise.eval(x / 20.0, y / 20.0);
        float hum = (float) oNoise.eval(0.2 * x / 20.0, 0.2 * y / 20.0);
        Cell c = new Cell(niv, hum);
        cells.put(k, c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @PostLoad
    private void postLoad(){
        for (Cell c : cellsSet) {
            cells.put(c.getPos(), c);
        }
    }

    @PrePersist
    @PreUpdate
    private void preSave(){
        cellsSet = new HashSet<>(cells.values());
    }
}