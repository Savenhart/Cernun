package fr.satysko.cernun_ai_proto.AI;

import fr.satysko.cernun_ai_proto.Agents.Creature;

import java.util.Arrays;
import java.util.List;

/**
 * AICreature
 */
public class AICreature {

    private Creature crea;
    private double reward;
    private int[] datasEntries;
    private int numberPossibleAction;
    private final double learningRate = 0.01;
    private int inputSpace;
    private Buffer buffer;

    public Creature getCrea() {
        return this.crea;
    }

    public void setCrea(Creature crea) {
        this.crea = crea;
    }

    public double getReward() {
        return this.reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public int[] getDatasEntries() {
        return this.datasEntries;
    }

    public void setDatasEntries(int[] datasEntries) {
        this.datasEntries = datasEntries;
    }

    public int getNumberPossibleAction() {
        return this.numberPossibleAction;
    }

    public void setNumberPossibleAction(int numberPossibleAction) {
        this.numberPossibleAction = numberPossibleAction;
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public Buffer getBuffer() {
        return this.buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public int getInputSpace() {
        return inputSpace;
    }

    public void setInputSpace(int inputSpace) {
        this.inputSpace = inputSpace;
    }

    public List<Object> movement(int action){
        return Arrays.asList();
    }

}