package fr.satysko.models;

import java.util.HashMap;
import java.util.Map;
import fr.satysko.utils.OpenSimplexNoise;

class World {

    Map<Key, Cell> world;
    String name;
    long seed;
    int id;

    OpenSimplexNoise oNoise;

    public World(String n, long s) {
        name = n;
        seed = s;
        oNoise = new OpenSimplexNoise(seed);
        world = new HashMap<Key, Cell>();
    }

    public void genCell(int x, int y) {
        Key k = new Key(x, y);
        float niv = (float) oNoise.eval((x + 00) / 20.0, (y + 00) / 20.0);
        float hum = (float) oNoise.eval(0.2 * (x + 00) / 20.0, 0.2 * (y + 00) / 20.0);
        Cell c = new Cell(niv, hum);
        world.put(k, c);
    }
}