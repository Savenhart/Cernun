package fr.satysko.models;

public class Creature {
    private String name;
    private int speed;
    private int energy;
    private int energyMax;
    private int perception;
    private int mass;
    private int diet;
    private int ratioSeaMountain;

    public Creature() {}

    public Creature(String name, int speed, int energy, int energyMax, int perception, int mass, int diet, int ratioSeaMountain) {
        this.name = name;
        this.speed = speed;
        this.energy = energy;
        this.energyMax = energyMax;
        this.perception = perception;
        this.mass = mass;
        this.diet = diet;
        this.ratioSeaMountain = ratioSeaMountain;
    }

    public void eat(){

    }

    public void move(){

    }

    public void see(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergyMax() {
        return energyMax;
    }

    public void setEnergyMax(int energyMax) {
        this.energyMax = energyMax;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getDiet() {
        return diet;
    }

    public void setDiet(int diet) {
        this.diet = diet;
    }

    public int getRatioSeaMountain() {
        return ratioSeaMountain;
    }

    public void setRatioSeaMountain(int ratioSeaMountain) {
        this.ratioSeaMountain = ratioSeaMountain;
    }
}
