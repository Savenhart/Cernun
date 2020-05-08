package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.satysko.cernun.utils.Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.hibernate.event.spi.MergeEvent;

import javax.persistence.*;
import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Creature extends Entite {
    @Transient
    private final static double MARGIN = 0.2;

    private String name;
    private int moveSpeed;
    private double movePoint;
    private double movePointUsed;
    // To DO : proportionnel au moveSpeed
    // private double attackPoint;
    private int energy;
    private int energyMax;
    private int perception;
    private int mass;
    private double diet;
    private double toleranceElevation;
    private double toleranceHumidity;
    private double toleranceTemperature;
    private int posX;
    private int posY;
    private double ratioExploration = 0.9;
    private double learningRate = 0.8;
    @Transient
    private Cell[] view;
    @Transient
    private double[] rewards;
    @Embedded
    @JsonIgnore
    private Brain brain;
    @OneToOne
    private Picture picture;
    @ManyToOne
    private UserWorld userWorld;

    public Creature() {}

    public Creature(int posX, int posY, UserWorld userWorld) {
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = (int) Math.round(Math.random() * 4 + 1);
        this.movePoint = (this.moveSpeed * 3 + 1) / 2;
        this.mass = (int) Math.round(Math.random() * 90 + 10);
        this.energyMax = 375 * this.mass;
        this.energy = this.energyMax;
        this.perception = (int) Math.round(Math.random() * 4 + 1);
        this.diet = Math.random();
        this.toleranceElevation = Math.random();
        this.toleranceHumidity = Math.random();
        this.toleranceTemperature = Math.random();
        this.brain = new Brain(this.perception);
        this.userWorld = userWorld;
        calculReward();
        generateName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getDiet() {
        return diet;
    }

    public void setDiet(int diet) {
        this.diet = diet;
    }

    public double getToleranceElevation() {
        return toleranceElevation;
    }

    public void setToleranceElevation(double toleranceElevation) {
        this.toleranceElevation = toleranceElevation;
    }

    public double getToleranceHumidity() {
        return toleranceHumidity;
    }

    public void setToleranceHumidity(double toleranceHumidity) {
        this.toleranceHumidity = toleranceHumidity;
    }

    public double getToleranceTemperature() {
        return toleranceTemperature;
    }

    public void setToleranceTemperature(double toleranceTemperature) {
        this.toleranceTemperature = toleranceTemperature;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMovePoint() {
        return movePoint;
    }

    public void setMovePoint(double movePoint) {
        this.movePoint = movePoint;
    }

    public void setDiet(double diet) {
        this.diet = diet;
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public UserWorld getUserWorld() {
        return userWorld;
    }

    public void setUserWorld(UserWorld userWorld) {
        this.userWorld = userWorld;
    }

    public void generateName(){
        String n = "";
        int nbSyllabe = (int) Math.floor(Math.random() * 2 + 2);
        for(int i = 0; i < nbSyllabe; i++){
            n += Utils.Syllabes[(int) Math.floor(Math.random() * Utils.Syllabes.length)];
        }
        this.name = n;
    }

    public void update(){
        this.movePointUsed = 0.0;
        do {
            if (Math.random() > ratioExploration){
                exploitation();
            } else {
                exploration();
            }
        } while (this.movePointUsed <= this.movePoint);
        //Calcul dépense journalière : mass * moveSpeed² / 8 + movePoint_use * mass * moveSpeed²/ (moveSpeed * 3 + 1)
        double metabolicEnergy = this.mass * Math.sqrt(this.moveSpeed) / 8;
        double cyneticEnergy = this.movePointUsed * this.mass * Math.sqrt(this.moveSpeed) / (this.moveSpeed * 3 + 1);
        int dailyExpense = (int) Math.round(metabolicEnergy + cyneticEnergy);
        this.energy -= dailyExpense;
    }

    public void exploitation(){
        Array2DRowRealMatrix res = brain.feedForward(new Array2DRowRealMatrix(makeInputs()));

        int idAction = 0;
        double temp = 0.0;
        for (int idx = 0; idx <res.getRowDimension(); idx ++){
            if (res.getEntry(idx,0) > temp){
                idAction = idx;
                temp = res.getEntry(idx,0);
            }
        }

        switch (idAction){
            case 0:
                move(0,1);
                break;
            case 1:
                move(1,1);
                break;
            case 2:
                move(1,0);
                break;
            case 3:
                move(0,-1);
                break;
            case 4:
                move(-1,0);
                break;
            case 5:
                move(-1,1);
                break;
            case 6:
                feed();
                break;
            case 7:
                attack();
                break;
        }
    }

    public void exploration(){
        double[][] valueInputs = makeInputs();
        Array2DRowRealMatrix res = brain.feedForward(new Array2DRowRealMatrix(valueInputs));

        // définition des limites pour faire de l'aléatoire pondéré
        double lim1 = res.getEntry(0,0);
        double lim2 = lim1 + res.getEntry(1,0);
        double lim3 = lim2 + res.getEntry(2,0);
        double lim4 = lim3 + res.getEntry(3,0);
        double lim5 = lim4 + res.getEntry(4,0);
        double lim6 = lim5 + res.getEntry(5,0);
        double lim7 = lim6 + res.getEntry(6,0);

        // Choix d'une action aléatoire
        int idAction;
        double choice = Math.random();
        if (choice < lim1){
            idAction = 0;
            move(0,1);
        } else if (choice < lim2) {
            idAction = 1;
            move(1,1);
        } else if (choice < lim3){
            idAction = 2;
            move(1,0);
        } else if (choice < lim4) {
            idAction = 3;
            move(0,-1);
        } else if (choice < lim5) {
            idAction = 1;
            move(-1,0);
        } else if (choice < lim6){
            idAction = 2;
            move(-1,1);
        } else if (choice < lim7){
            idAction = 6;
            feed();
        } else {
            idAction = 7;
            attack();
        }

        Cell currentCell = userWorld.getWorld().getCell(this.posX, this.posY);
        double costMP = calculCostMP(currentCell);
        double reward = 2 * costMP / 3 + 1;

        if (reward != 0){

            // prépartion entrainement
            Array2DRowRealMatrix inputs = new Array2DRowRealMatrix(valueInputs);

            Array2DRowRealMatrix targets = new Array2DRowRealMatrix(new double[][]{
                    {idAction == 0 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 1 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 2 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 3 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 4 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 5 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 6 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14},
                    {idAction == 7 ? reward > 0 ? 1 : 0 : reward > 0 ? 0 : 0.14}
            });

            brain.train(inputs, targets, learningRate);
            ratioExploration -= 0.01;
        }
    }

    public void mutate(){

    }

    public void feed(){
        World world = userWorld.getWorld();
        Location location = new Location();
        location.setPos(new Vector2d(this.posX, this.posY));
        this.energy += world.searchAndDestroyFood(location);
        if(this.energy> this.energyMax){
            this.energy = this.energyMax;
        }
    }

    public void move(int moveX, int moveY){
        this.posX += moveX;
        this.posY += moveY;
        this.movePointUsed += calculCostMP(userWorld.getWorld().getCell(this.posX, this.posY));
        calculReward();
    }

    public String attack(){
        return null;
    }

    public void calculReward(){
        World world = userWorld.getWorld();

        this.view = world.getView(this.posX,this.posY,this.perception);
        ArrayList<Double> rewardsTemp = new ArrayList<>();
        for (int i = 0; i < this.view.length; i++) {
            Cell cell = this.view[i];
            Location location = cell.getLocation();
            double costMP = calculCostMP(cell);
            // Prise en compte de la nourriture
            if (world.ifFoodExist(location)){
                costMP--;
            }
            double reward = 2 * costMP / 3 + 1;
            rewardsTemp.add(reward);
        }
        this.rewards = ArrayUtils.toPrimitive(rewardsTemp.toArray(new Double[0]));
    }

    public double calculCostMP(Cell cell){
        // Récupération des valeurs de la cellule
        double valueE = cell.getBiome().getElevation();
        double valueT = cell.getBiome().getTemperature();
        double valueH = cell.getBiome().getHumidity();
        // Calcul des coût des Move Point, basée sur la formule :
        // MP = ( abs(value - tolerance) <= marge ? 0 : abs(value - tolerance) - marge ) / ( 2 - marge)
        double costMPE = (Math.abs(valueE - toleranceElevation) <= MARGIN ? 0 : Math.abs(valueE - toleranceElevation) - MARGIN) / (2 - MARGIN);
        double costMPT = (Math.abs(valueT - toleranceTemperature) <= MARGIN ? 0 : Math.abs(valueE - toleranceTemperature) - MARGIN) / (2 - MARGIN);
        double costMPH = (Math.abs(valueE - toleranceHumidity) <= MARGIN ? 0 : Math.abs(valueE - toleranceHumidity) - MARGIN) / (2 - MARGIN);
        return 1 + costMPE + costMPH + costMPT;
    }

    public double[][] makeInputs(){
        Location location = new Location();
        location.setPos(new Vector2d(this.posX,this.posY));

        double[][] inputs = new double[rewards.length + 2][1];
        // premier input : a quel point la créature a t'elle faim ?
        inputs[0][0] = this.energy > (this.energyMax * 0.7) ? 0 : - 10 * this.energy / ( 7 * this.energyMax) + 1 ;
        // deuxième inputs : présence de nourriture sur la case
        inputs[1][0] = userWorld.getWorld().ifFoodExist(location) ? 1 : 0;
        // reste des inputs : vue de la créature
        for (int i=0; i < this.rewards.length; i++){
            inputs[i + 2][0] = this.rewards[i];
        }
        return inputs;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", moveSpeed=" + moveSpeed +
                ", movePoint=" + movePoint +
                ", movePointUsed=" + movePointUsed +
                ", energy=" + energy +
                ", energyMax=" + energyMax +
                ", perception=" + perception +
                ", mass=" + mass +
                ", diet=" + diet +
                ", toleranceElevation=" + toleranceElevation +
                ", toleranceHumidity=" + toleranceHumidity +
                ", toleranceTemperature=" + toleranceTemperature +
                ", posX=" + posX +
                ", posY=" + posY +
                ", ratioExploration=" + ratioExploration +
                ", learningRate=" + learningRate +
                ", userWorld=" + userWorld +
                '}';
    }

    @PostLoad
    private void postLoad(){
        calculReward();
    }
}
