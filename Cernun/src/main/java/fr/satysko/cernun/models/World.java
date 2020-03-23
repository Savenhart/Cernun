package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.satysko.cernun.utils.OpenSimplexNoise;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class World extends Entite {

    @Column(unique = true)
    private String name;
    private long seed;

    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Set<Cell> cellsSet = new HashSet<>();
    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Set<Food> foodsSet = new HashSet<>();
    @OneToMany(mappedBy = "world", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Set<UserWorld> userWorlds;

    @Transient
    @JsonIgnore
    private Map<Location, Cell> cells = new HashMap<>();
    @Transient
    @JsonIgnore
    private Map<Location, Food> foods = new HashMap<>();
    @Transient
    @JsonIgnore
    OpenSimplexNoise oNoise;


    public World(){}

    public World(String n, long s) {
        name = n;
        seed = s;
        oNoise = new OpenSimplexNoise(seed);
    }

    public Cell genCell(int x, int y) {
        Location k = new Location();
        k.setPos(new Vector2d(x, y));
//        //Génération du bruit pour l'élévation en fonction des coordonnées
//        float niv = (float) (oNoise.eval(x ,y ) + oNoise.eval(x *2 ,y * 2 ) / 2 + oNoise.eval(x * 4,y * 4 ) / 4);
//        //Génération du bruit pour l'élévation en fonction des coordonnées
//        float hum = (float) oNoise.eval(0.2 * x / 20.0, 0.2 * y / 20.0);
//        //Génération du bruit pour l'élévation en fonction des coordonnées
//        float tem = (float) oNoise.eval(0.2 * x / 20.0, 0.2 * y / 20.0);

        float niv0 = (float) oNoise.eval(x / 20.0, y / 20.0);
        float niv1 = (float) (0.5 * oNoise.eval(x / 10.0, y / 10.0) * niv0);
        float niv2 = (float) (0.25 * oNoise.eval(x / 5.0, y / 5.0) * (niv0 + niv1));
        float niv = niv0 + niv1 + niv2;

        float hum0 = (float) oNoise.eval(x / 60.0, y / 60.0);
        float hum1 = (float) (0.5 * oNoise.eval(x / 30.0, y / 30.0) * hum0);
        float hum2 = (float) (0.25 * oNoise.eval(x / 15.0, y / 15.0) * (hum0 + hum1));
        float hum = hum0 + hum1 + hum2;

        float tem0 = (float) oNoise.eval(x / 100.0 , y / 100.0 );
        float tem1 = (float) (0.5 * oNoise.eval(2 * x / 100.0 , 2 * y / 100.0 ) * hum0);
        float tem2 = (float) (0.25 * oNoise.eval(4 * x / 100.0 , 4 * y / 100.0 ) * (hum0 + hum1));
        float tem = tem0 + tem1 + tem2;

        Cell c = new Cell(niv, hum, tem);
        c.setWorld(this);
        c.setLocation(k);
        cells.put(k, c);
        return c;
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

    public Set<UserWorld> getUserWorlds() {
        return userWorlds;
    }

    public void setUserWorlds(Set<UserWorld> userWorlds) {
        this.userWorlds = userWorlds;
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
        oNoise = new OpenSimplexNoise(seed);
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
