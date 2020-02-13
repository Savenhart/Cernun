package fr.satysko.models;

import fr.satysko.utils.OpenSimplexNoise;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class World extends Entite {

    private String name;
    private long seed;

    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Cell> cellsSet = new HashSet<>();
    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Food> foodsSet = new HashSet<>();
    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Appartenance> appartenances;

    @Transient
    private Map<Location, Cell> cells = new HashMap<>();
    @Transient
    private Map<Location, Food> foods = new HashMap<>();
    @Transient
    OpenSimplexNoise oNoise;

    public World(){}

    public World(String n, long s) {
        name = n;
        seed = s;
        oNoise = new OpenSimplexNoise(seed);
    }

    public void genCell(int x, int y) {
        Location k = new Location();
        k.setPos(new Vector2d(x, y));
        float niv = (float) oNoise.eval(x / 20.0, y / 20.0);
        float hum = (float) oNoise.eval(0.2 * x / 20.0, 0.2 * y / 20.0);
        Cell c = new Cell(niv, hum);
        c.setWorld(this);
        c.setLocation(k);
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

    public Set<Appartenance> getAppartenances() {
        return appartenances;
    }

    public void setAppartenances(Set<Appartenance> appartenances) {
        this.appartenances = appartenances;
    }

    public Map<Location, Cell> getCells() {
        return cells;
    }

    public void setCells(Map<Location, Cell> cells) {
        this.cells = cells;
    }

    public Map<Location, Food> getFoods() {
        return foods;
    }

    public void setFoods(Map<Location, Food> foods) {
        this.foods = foods;
    }

    @PostLoad
    private void postLoad(){
        for (Cell c : cellsSet) {
            cells.put(c.getLocation(), c);
        }
        for (Food f : foodsSet) {
            foods.put(f.getLocation(), f);
        }
    }

    @PrePersist
    @PreUpdate
    private void preSave(){
        cellsSet = new HashSet<>(cells.values());
        foodsSet = new HashSet<>(foods.values());
    }
}
