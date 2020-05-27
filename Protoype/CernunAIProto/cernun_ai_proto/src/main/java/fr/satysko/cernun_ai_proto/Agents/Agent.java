package fr.satysko.cernun_ai_proto.Agents;


import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class Agent {

    private double lambda;
    Brain brain;
    private int posX;
    private int posY;
    private int cols;
    private int rows;
    private double[][] view = new double[3][3];
    private double learningRate;
    World w;

    private boolean explor = false;

    public Agent(int posX, int posY, int cols, int rows, World w){
        this.posX = posX;
        this.posY = posY;
        this.cols = cols;
        this.rows = rows;
        brain = new Brain();
        this.w = w;
        this.lambda = 0.9;
        this.learningRate = 0.8;

        view = w.getView(posX, posY, 1);
    }
    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
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

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public double[][] getView() {
        return view;
    }

    public void setView(double[][] view) {
        this.view = view;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public boolean isExplor() {
        return explor;
    }

    public void setExplor(boolean explor) {
        this.explor = explor;
    }

    public void update(){
        if(random(1) < lambda){
            explor =true;
            exploration();
        } else {
            explor = false;
            exploration();
        }
    }

    // Exploit / Explor
    public  void exploitation(){
        Array2DRowRealMatrix res = brain.feedForward(new Array2DRowRealMatrix(new double[][] {
                {view[0][0]},
                {view[0][1]},
                {view[0][2]},
                {view[1][0]},
                {view[1][2]},
                {view[2][0]},
                {view[2][1]},
                {view[2][2]}
        }));

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
                up();
                break;
            case 1:
                right();
                break;
            case 2:
                down();
                break;
            case 3:
                left();
                break;
        }
    }

    public void exploration(){
        Array2DRowRealMatrix res = brain.feedForward(new Array2DRowRealMatrix(new double[][] {
                {view[0][0]},
                {view[0][1]},
                {view[0][2]},
                {view[1][0]},
                {view[1][2]},
                {view[2][0]},
                {view[2][1]},
                {view[2][2]}
        }));

        // définition des limites pour faire de l'aléatoire pondéré
        double lim1 = res.getEntry(0,0);
        double lim2 = lim1 + res.getEntry(1,0);
        double lim3 = lim2 + res.getEntry(2,0);

        // Choix d'une action aléatoire
        int idAction;
        double choice = random(1);
        if (choice <lim1){
            idAction = 0;
            up();
        } else if (choice <lim2) {
            idAction = 1;
            right();
        } else if (choice < lim3){
            idAction = 2;
            down();
        } else {
            idAction = 3;
            left();
        }

        double reward = w.getRewards(posX, posY);

        if (reward != 0){
            switch (idAction){
                case 0:
                    down();
                    break;
                case 1:
                    left();
                    break;
                case 2:
                    up();
                    break;
                case 3:
                    right();
                    break;
            }

            // prépartion entrainement
            Array2DRowRealMatrix inputs = new Array2DRowRealMatrix(new  double[][]{
                    {view[0][0]},
                    {view[0][1]},
                    {view[0][2]},
                    {view[1][0]},
                    {view[1][2]},
                    {view[2][0]},
                    {view[2][1]},
                    {view[2][2]}
            });

            Array2DRowRealMatrix targets = new Array2DRowRealMatrix(new double[][]{
                    {idAction == 0 ? reward : reward > 0 ? 0 : 0.33},
                    {idAction == 1 ? reward : reward > 0 ? 0 : 0.33},
                    {idAction == 2 ? reward : reward > 0 ? 0 : 0.33},
                    {idAction == 3 ? reward : reward > 0 ? 0 : 0.33}
            });

            brain.train(inputs, targets, learningRate);

            // reset
            posX = rows -1;
            posY = cols - 1;
            lambda = lambda -0.005;
        }
    }

    // Action
     public void up(){
        posY = (posY - 1) < 0 ? cols - 1 : posY - 1;
        view = w.getView(posX, posY, 1);
    }

    public void right(){
        posX = (posX - 1) < 0 ? rows - 1 : posX - 1;
        view = w.getView(posX, posY, 1);
    }

    public void down(){
        posY = (posY + 1) % cols;
        view = w.getView(posX, posY, 1);
    }

    public void left(){
        posX = (posX + 1) % rows;
        view = w.getView(posX, posY, 1);
    }
}