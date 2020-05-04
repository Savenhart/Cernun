package fr.satysko.cernun.models;

import fr.satysko.cernun.utils.OpenSimplexNoise;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Chunk {

    @Transient
    private static final int SIZECHUNK = 25;

    private int posX;
    private int posY;

    @ManyToOne
    private World world;

    @OneToMany(mappedBy = "chunk", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Cell> cellsSet = new HashSet<>();
    @Transient
    @JsonIgnore
    private Map<Location, Cell> cells = new HashMap<>();

    public Chunk() {
    }

    public Chunk(World world, int posX, int posY, OpenSimplexNoise oNoise) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        generate(oNoise);
    }

    public void generate(OpenSimplexNoise oNoise){
        for(int dX = 0; dX < SIZECHUNK; dX++){
            for(int dY = 0; dY < SIZECHUNK; dY++){
                Location k = new Location();
                k.setPos(new Vector2d(posX + dX, posY + dY));

                Cell c = new Cell();
                c.setLocation(k);
                cells.put(k, c);
            }
        }
    }

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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Set<Cell> getCellsSet() {
        return cellsSet;
    }

    public void setCellsSet(Set<Cell> cellsSet) {
        this.cellsSet = cellsSet;
    }

    public Map<Location, Cell> getCells() {
        return cells;
    }

    public void setCells(Map<Location, Cell> cells) {
        this.cells = cells;
    }

    public Cell genCell(int x, int y) {
        Location k = new Location();
        k.setPos(new Vector2d(x, y));

        double niv0 = oNoise.eval(x / 20.0, y / 20.0);
        double niv1 = 0.5 * oNoise.eval(x / 10.0, y / 10.0) * niv0;
        double niv2 = 0.25 * oNoise.eval(x / 5.0, y / 5.0) * (niv0 + niv1);
        double niv = niv0 + niv1 + niv2;

        double hum0 = oNoise.eval(x / 60.0, y / 60.0);
        double hum1 = 0.5 * oNoise.eval(x / 30.0, y / 30.0) * hum0;
        double hum2 = 0.25 * oNoise.eval(x / 15.0, y / 15.0) * (hum0 + hum1);
        double hum = hum0 + hum1 + hum2;

        double tem0 = oNoise.eval(x / 100.0 , y / 100.0 );
        double tem1 = 0.5 * oNoise.eval(2 * x / 100.0 , 2 * y / 100.0 ) * hum0;
        double tem2 = 0.25 * oNoise.eval(4 * x / 100.0 , 4 * y / 100.0 ) * (hum0 + hum1);
        double tem = tem0 + tem1 + tem2;

        Cell c = new Cell(niv, hum, tem);
        c.setWorld(this);
        c.setLocation(k);
        cells.put(k, c);

        return c;
    }

    @PostLoad
    private void postLoad(){
        for (Cell c : cellsSet) {
            cells.put(c.getLocation(), c);
        }
    }

    @PrePersist
    @PreUpdate
    private void preSave(){
        cellsSet = new HashSet<>(cells.values());
    }
}
