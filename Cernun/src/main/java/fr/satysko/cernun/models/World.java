package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.satysko.cernun.repositories.FoodRepository;
import fr.satysko.cernun.utils.OpenSimplexNoise;
import org.springframework.beans.factory.annotation.Autowired;
import sun.management.Agent;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

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

    public Cell getCell(int x, int y){
        Location k = new Location();
        k.setPos(new Vector2d(x, y));
        return cells.get(k);
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

    public Food genFood(int x, int y, int rank){
        Location k = new Location();
        k.setPos(new Vector2d(x, y));

        double max = 0.0;
        double val = 0.0;
        for (int nx = x - rank; nx <= x + rank; nx++) {
            for (int ny = y - rank; ny <= y + rank; ny++) {
                double temp = oNoise.eval(nx, ny);
                if (temp > max){
                    max = temp;
                }
                if (nx == x && ny == y){
                    val = temp;
                }
            }
        }
        if(max == val){
            Food f = new Food();
            f.setWorld(this);
            f.setLocation(k);
            return f;
        }
        return null;
    }

    public Cell[] getView(int posX, int posY, int perception){
        int nbInput = 1;
        switch (perception){
            case 1:
                nbInput = 7;
                break;
            case 2:
                nbInput = 19;
                break;
            case 3:
                nbInput = 37;
                break;
            case 4:
                nbInput = 61;
                break;
            case 5:
                nbInput = 91;
                break;
        }
        Cell[] res = new Cell[nbInput];
       if (perception > 0){
           res[0] = getCell(posX,posY + 1);
           res[1] = getCell(posX + 1,posY + 1);
           res[2] = getCell(posX + 1,posY);
           res[3] = getCell(posX,posY - 1);
           res[4] = getCell(posX - 1,posY);
           res[5] = getCell(posX - 1,posY + 1);
       }

        if (perception > 1){
            res[6] = getCell(posX,posY + 2);
            res[7] = getCell(posX + 1,posY + 2);
            res[8] = getCell(posX + 2,posY + 1);
            res[9] = getCell(posX + 2,posY);
            res[10] = getCell(posX + 2,posY - 1);
            res[11] = getCell(posX + 1,posY - 1);
            res[12] = getCell(posX,posY - 2);
            res[13] = getCell(posX - 1,posY - 1);
            res[14] = getCell(posX - 2,posY - 1);
            res[15] = getCell(posX - 2,posY);
            res[16] = getCell(posX - 2,posY + 1);
            res[17] = getCell(posX - 1,posY + 2);
        }

        if (perception > 2){
            res[18] = getCell(posX,posY + 3);
            res[19] = getCell(posX + 1,posY + 3);
            res[20] = getCell(posX + 2,posY + 2);
            res[21] = getCell(posX + 3,posY + 2);
            res[22] = getCell(posX + 3,posY + 1);
            res[23] = getCell(posX + 3,posY);
            res[24] = getCell(posX + 3,posY - 1);
            res[25] = getCell(posX + 2,posY - 2);
            res[26] = getCell(posX + 1,posY - 2);
            res[27] = getCell(posX,posY - 3);
            res[28] = getCell(posX - 1,posY - 2);
            res[29] = getCell(posX - 2,posY - 2);
            res[30] = getCell(posX - 3,posY - 1);
            res[31] = getCell(posX - 3,posY);
            res[32] = getCell(posX - 3,posY + 1);
            res[33] = getCell(posX - 3,posY + 2);
            res[34] = getCell(posX - 2 ,posY + 2);
            res[35] = getCell(posX - 1,posY + 3);
        }

        if (perception > 3){
            res[36] = getCell(posX,posY + 4);
            res[37] = getCell(posX + 1,posY + 4);
            res[38] = getCell(posX + 2,posY + 3);
            res[39] = getCell(posX + 3,posY + 3);
            res[40] = getCell(posX + 4,posY + 2);
            res[41] = getCell(posX + 4,posY + 1);
            res[42] = getCell(posX + 4,posY);
            res[43] = getCell(posX + 4,posY - 1);
            res[44] = getCell(posX + 4,posY - 2);
            res[45] = getCell(posX + 3,posY - 2);
            res[46] = getCell(posX + 2,posY - 3);
            res[47] = getCell(posX + 1,posY - 3);
            res[48] = getCell(posX,posY - 4);
            res[49] = getCell(posX - 1,posY - 3);
            res[50] = getCell(posX - 2,posY - 3);
            res[51] = getCell(posX - 3,posY - 2);
            res[52] = getCell(posX - 4,posY - 2);
            res[53] = getCell(posX - 4,posY - 1);
            res[54] = getCell(posX - 4,posY);
            res[55] = getCell(posX - 4,posY + 1);
            res[56] = getCell(posX - 4,posY + 2);
            res[57] = getCell(posX - 3,posY + 3);
            res[58] = getCell(posX - 2,posY + 3);
            res[59] = getCell(posX - 1,posY + 4);
        }

        if (perception > 4){
            res[60] = getCell(posX,posY + 5);
            res[61] = getCell(posX + 1,posY + 5);
            res[62] = getCell(posX + 2,posY + 4);
            res[63] = getCell(posX + 3,posY + 4);
            res[64] = getCell(posX + 4,posY + 3);
            res[65] = getCell(posX + 5,posY + 3);
            res[66] = getCell(posX + 5,posY + 2);
            res[67] = getCell(posX + 5,posY + 1);
            res[68] = getCell(posX + 5,posY);
            res[69] = getCell(posX + 5,posY - 1);
            res[70] = getCell(posX + 5,posY - 2);
            res[71] = getCell(posX + 4,posY - 3);
            res[72] = getCell(posX + 3,posY - 3);
            res[73] = getCell(posX + 2,posY - 4);
            res[74] = getCell(posX + 1,posY - 4);
            res[75] = getCell(posX,posY - 5);
            res[76] = getCell(posX - 1,posY - 4);
            res[77] = getCell(posX - 2,posY - 4);
            res[78] = getCell(posX - 3,posY - 3);
            res[79] = getCell(posX - 4,posY - 3);
            res[80] = getCell(posX - 5,posY - 2);
            res[81] = getCell(posX - 5,posY - 1);
            res[82] = getCell(posX - 5,posY);
            res[83] = getCell(posX - 5,posY + 1);
            res[84] = getCell(posX - 5,posY + 2);
            res[85] = getCell(posX - 5,posY + 3);
            res[86] = getCell(posX - 4,posY + 3);
            res[87] = getCell(posX - 3,posY + 4);
            res[88] = getCell(posX - 2,posY + 4);
            res[89] = getCell(posX - 1,posY + 5);
        }

        return res;
    }

    public boolean ifFoodExist(Location location){
        return foods.get(location) != null;
    }

    public int searchAndDestroyFood(Location location){
        Food food = foods.get(location);
        if (food == null){
            return 0;
        }
        int energy = food.getEnergy();
        foods.remove(location);
        return energy;
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
