package fr.satysko.models;

import java.util.HashMap;
import java.util.Map;
import fr.satysko.utils.OpenSimplexNoise;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
class World extends Entite{

    String name;
    long seed;

    Map<Key, Cell> cells;
    Map<Key, Food> foods;
    @Transient
    OpenSimplexNoise oNoise;

    public World(){}

    public World(String n, long s) {
        name = n;
        seed = s;
        oNoise = new OpenSimplexNoise(seed);
        cells = new HashMap<Key, Cell>();
    }

    public void genCell(int x, int y) {
        Key k = new Key(x, y);
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
}