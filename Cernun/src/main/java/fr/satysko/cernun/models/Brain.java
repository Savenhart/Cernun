package fr.satysko.cernun.models;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import javax.persistence.Embeddable;

@Embeddable
public class Brain {

    private Array2DRowRealMatrix weight_i;
    private Array2DRowRealMatrix weight_ih;
    private Array2DRowRealMatrix weight_hh;
    private Array2DRowRealMatrix weight_ho;

    private Array2DRowRealMatrix bias_i;
    private Array2DRowRealMatrix bias_ih;
    private Array2DRowRealMatrix bias_hh;
    private Array2DRowRealMatrix bias_ho;

    /*
    10 données d'entrées
    10 neurones d'entrées
    8 neurones sur la première couche cachés
    6 neurones sur la seconde couche cachés
    9 neurones en sorties
     */

    public Brain(int perception) {
        int nbInput = 2;
        for(int i =1 ; i<= perception ; i++){
            nbInput += 6*i;
        }
        this.weight_i = new Array2DRowRealMatrix(10,nbInput);
        this.weight_ih = new Array2DRowRealMatrix(8,10);
        this.weight_hh= new Array2DRowRealMatrix(6,8);
        this.weight_ho = new Array2DRowRealMatrix(9,6);

        this.bias_i = new Array2DRowRealMatrix(10,1);
        this.bias_ih = new Array2DRowRealMatrix(8,1);
        this.bias_hh = new Array2DRowRealMatrix(6,1);
        this.bias_ho = new Array2DRowRealMatrix(9,1);

        // Intialisation des matrices de poids
        for(int i = 0; i< weight_i.getRowDimension(); i++){
            for(int j= 0; j < weight_i.getColumnDimension(); j++){
                weight_i.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< weight_ih.getRowDimension(); i++){
            for(int j= 0; j < weight_ih.getColumnDimension(); j++){
                weight_ih.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< weight_hh.getRowDimension(); i++){
            for(int j= 0; j < weight_hh.getColumnDimension(); j++){
                weight_hh.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< weight_ho.getRowDimension(); i++){
            for(int j= 0; j < weight_ho.getColumnDimension(); j++){
                weight_ho.setEntry(i, j,Math.random());
            }
        }

        // Intialisation des matrices de bias
        for(int i = 0; i< bias_i.getRowDimension(); i++){
            for(int j= 0; j < bias_i.getColumnDimension(); j++){
                bias_i.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< bias_ih.getRowDimension(); i++){
            for(int j= 0; j < bias_ih.getColumnDimension(); j++){
                bias_ih.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< bias_hh.getRowDimension(); i++){
            for(int j= 0; j < bias_hh.getColumnDimension(); j++){
                bias_hh.setEntry(i, j,Math.random());
            }
        }

        for(int i = 0; i< bias_ho.getRowDimension(); i++){
            for(int j= 0; j < bias_ho.getColumnDimension(); j++){
                bias_ho.setEntry(i, j,Math.random());
            }
        }
    }

    public Brain(Brain brain) {
        this.weight_i = brain.getWeight_i();
        this.weight_ih = brain.getWeight_ih();
        this.weight_hh = brain.getWeight_hh();
        this.weight_ho = brain.getWeight_ho();

        this.bias_i = brain.getBias_i();
        this.bias_ih = brain.getBias_ih();
        this.bias_hh = brain.getBias_hh();
        this.bias_ho = brain.getBias_ho();

    }

    Array2DRowRealMatrix feedForward(Array2DRowRealMatrix mat){
        if (mat.getRowDimension() != weight_i.getColumnDimension()) {
            return null;
        }

        Array2DRowRealMatrix lastLayer = mat;

        // Calcul de sortie des neurones d'entrées
        Array2DRowRealMatrix temp = weight_i.multiply(lastLayer);
        temp = temp.add(this.bias_i);
        lastLayer = activation(temp);

        // Calcul de sortie de la première couche de neurones cachés
        temp = weight_ih.multiply(lastLayer);
        temp = temp.add(this.bias_ih);
        lastLayer = activation(temp);

        // Calcul de sortie de la seconde couche de neurones cachés
        temp = weight_hh.multiply(lastLayer);
        temp = temp.add(this.bias_hh);
        lastLayer = activation(temp);

        // Calcul de sortie des neurones de sorties
        temp = weight_ho.multiply(lastLayer);
        temp = temp.add(this.bias_ho);
        lastLayer = activation(temp);

        return softMax(lastLayer);
    }

    void train(Array2DRowRealMatrix input, Array2DRowRealMatrix target, double learningRate){

        // Vérifications
        if (input.getRowDimension() != weight_i.getColumnDimension() ||
                input.getColumnDimension() != 1 ||
                target.getColumnDimension() != 1 ||
                target.getRowDimension() != 9) {
            return;
        }

        // Calcul des valeurs intermédiaires
        Array2DRowRealMatrix temp_i = weight_i.multiply(input);
        temp_i = temp_i.add(this.bias_i);
        Array2DRowRealMatrix layer_input = activation(temp_i);

        Array2DRowRealMatrix temp_ih = weight_ih.multiply(layer_input);
        temp_ih = temp_ih.add(this.bias_ih);
        Array2DRowRealMatrix layer_pHidden = activation(temp_ih);


        Array2DRowRealMatrix temp_hh = weight_hh.multiply(layer_pHidden);
        temp_hh = temp_hh.add(this.bias_hh);
        Array2DRowRealMatrix layer_sHidden = activation(temp_hh);

        Array2DRowRealMatrix temp_ho = weight_ho.multiply(layer_sHidden);
        temp_ho = temp_ho.add(this.bias_ho);
        Array2DRowRealMatrix layer_output = activation(temp_ho);


        // Calcul des erreurs de sortie de neurone
        Array2DRowRealMatrix error_output = target.subtract(layer_output);

        Array2DRowRealMatrix temp_error_ho = new Array2DRowRealMatrix(9,1);
        for (int i = 0; i < 9 ; i++){
            double total = 0.0;
            for (int j = 0; j < 6; j++){
                total += weight_ho.getEntry(i,j);
            }
            temp_error_ho.setEntry(i,0,error_output.getEntry(i,0)/total);
        }
        Array2DRowRealMatrix weight_hot = (Array2DRowRealMatrix) weight_ho.transpose();
        Array2DRowRealMatrix error_sHidden = weight_hot.multiply(temp_error_ho);

        Array2DRowRealMatrix temp_error_hh = new Array2DRowRealMatrix(6,1);
        for (int i = 0; i < 6 ; i++){
            double total = 0.0;
            for (int j = 0; j < 8; j++){
                total += weight_hh.getEntry(i,j);
            }
            temp_error_hh.setEntry(i,0, error_sHidden.getEntry(i,0)/total);
        }
        Array2DRowRealMatrix weight_hht = (Array2DRowRealMatrix) weight_hh.transpose();
        Array2DRowRealMatrix error_pHidden = weight_hht.multiply(temp_error_hh);

        Array2DRowRealMatrix temp_error_ih = new Array2DRowRealMatrix(8,1);
        for (int i = 0; i < 8 ; i++){
            double total = 0.0;
            for (int j = 0; j < 10; j++){
                total += weight_ih.getEntry(i,j);
            }
            temp_error_ih.setEntry(i,0, error_pHidden.getEntry(i,0)/total);
        }
        Array2DRowRealMatrix weight_iht = (Array2DRowRealMatrix) weight_ih.transpose();
        Array2DRowRealMatrix error_input = weight_iht.multiply(temp_error_ih);


        // Calcul des dérivées de chaque couches
        Array2DRowRealMatrix dSigmoid_output = new Array2DRowRealMatrix(layer_output.getRowDimension(), layer_output.getColumnDimension());
        for (int i = 0; i < dSigmoid_output.getRowDimension(); i++) {
            for (int j = 0; j < dSigmoid_output.getColumnDimension(); j++) {
                dSigmoid_output.setEntry(i,j,  layer_output.getEntry(i,j) * ( 1 - layer_output.getEntry(i,j)));
            }
        }

        Array2DRowRealMatrix dSigmoid_sHidden = new Array2DRowRealMatrix(layer_sHidden.getRowDimension(), layer_sHidden.getColumnDimension());
        for (int i = 0; i < dSigmoid_sHidden.getRowDimension(); i++) {
            for (int j = 0; j < dSigmoid_sHidden.getColumnDimension(); j++) {
                dSigmoid_sHidden.setEntry(i,j,  layer_sHidden.getEntry(i,j) * ( 1 - layer_sHidden.getEntry(i,j)));
            }
        }

        Array2DRowRealMatrix dSigmoid_pHidden = new Array2DRowRealMatrix(layer_pHidden.getRowDimension(), layer_pHidden.getColumnDimension());
        for (int i = 0; i < dSigmoid_pHidden.getRowDimension(); i++) {
            for (int j = 0; j < dSigmoid_pHidden.getColumnDimension(); j++) {
                dSigmoid_pHidden.setEntry(i,j,  layer_pHidden.getEntry(i,j) * ( 1 - layer_pHidden.getEntry(i,j)));
            }
        }

        Array2DRowRealMatrix dSigmoid_input = new Array2DRowRealMatrix(layer_input.getRowDimension(), layer_input.getColumnDimension());
        for (int i = 0; i < dSigmoid_input.getRowDimension(); i++) {
            for (int j = 0; j < dSigmoid_input.getColumnDimension(); j++) {
                dSigmoid_input.setEntry(i,j,  layer_input.getEntry(i,j) * ( 1 - layer_input.getEntry(i,j)));
            }
        }


        // Calcul du gradient de chaque couches
        Array2DRowRealMatrix gradient_output = error_output;
        for (int i = 0; i < gradient_output.getRowDimension(); i++) {
            for (int j = 0; j < gradient_output.getColumnDimension(); j++) {
                gradient_output.multiplyEntry(i,j, dSigmoid_output.getEntry(i,j));
            }
        }

        Array2DRowRealMatrix gradient_sHidden = error_sHidden;
        for (int i = 0; i < gradient_sHidden.getRowDimension(); i++) {
            for (int j = 0; j < gradient_sHidden.getColumnDimension(); j++) {
                gradient_sHidden.multiplyEntry(i,j, dSigmoid_sHidden.getEntry(i,j));
            }
        }

        Array2DRowRealMatrix gradient_pHidden = error_pHidden;
        for (int i = 0; i < gradient_pHidden.getRowDimension(); i++) {
            for (int j = 0; j < gradient_pHidden.getColumnDimension(); j++) {
                gradient_pHidden.multiplyEntry(i,j, dSigmoid_pHidden.getEntry(i,j));
            }
        }

        Array2DRowRealMatrix gradient_input = error_input;
        for (int i = 0; i < gradient_input.getRowDimension(); i++) {
            for (int j = 0; j < gradient_input.getColumnDimension(); j++) {
                gradient_input.multiplyEntry(i,j, dSigmoid_input.getEntry(i,j));
            }
        }


        // Calcul des deltas de chaque couches
        Array2DRowRealMatrix dBias_ho = (Array2DRowRealMatrix) gradient_output.scalarMultiply(learningRate);
        Array2DRowRealMatrix dWeight_ho = (Array2DRowRealMatrix) dBias_ho.multiply(layer_sHidden.transpose());

        Array2DRowRealMatrix dBias_hh = (Array2DRowRealMatrix) gradient_sHidden.scalarMultiply(learningRate);
        Array2DRowRealMatrix dWeight_hh = (Array2DRowRealMatrix) dBias_hh.multiply(layer_pHidden.transpose());

        Array2DRowRealMatrix dBias_ih = (Array2DRowRealMatrix) gradient_pHidden.scalarMultiply(learningRate);
        Array2DRowRealMatrix dWeight_ih = (Array2DRowRealMatrix) dBias_ih.multiply(layer_input.transpose());

        Array2DRowRealMatrix dBias_i = (Array2DRowRealMatrix) gradient_input.scalarMultiply(learningRate);
        Array2DRowRealMatrix dWeight_i = (Array2DRowRealMatrix) dBias_i.multiply(input.transpose());


        // Application des deltas des couches
        bias_ho = bias_ho.add(dBias_ho);
        weight_ho = weight_ho.add(dWeight_ho);

        bias_hh = bias_hh.add(dBias_hh);
        weight_hh = weight_hh.add(dWeight_hh);

        bias_ih = bias_ih.add(dBias_ih);
        weight_ih = weight_ih.add(dWeight_ih);

        bias_i = bias_i.add(dBias_i);
        weight_i = weight_i.add(dWeight_i);


    }

    Array2DRowRealMatrix activation(Array2DRowRealMatrix m){
        Array2DRowRealMatrix res = new Array2DRowRealMatrix(m.getRowDimension(), m.getColumnDimension());
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                res.setEntry(i,j,  1 / (1 + Math.pow(Math.E, -1 * m.getEntry(i,j))));
            }
        }
        return res;
    }

    public Array2DRowRealMatrix softMax(Array2DRowRealMatrix m){
        if (m.getColumnDimension() != 1) {
            return null;
        }
        Array2DRowRealMatrix res = new Array2DRowRealMatrix(m.getRowDimension(), m.getColumnDimension());

        double total = 0.0;
        for (int i = 0; i < m.getRowDimension(); i++) {
            total += m.getEntry(i,0);
        }

        for (int i = 0; i < m.getRowDimension(); i++) {
            res.setEntry(i,0, Math.round((m.getEntry(i,0) / total) * 100) / 100.0);
        }

        return res;
    }

    public Array2DRowRealMatrix getWeight_i() {
        return weight_i;
    }

    public void setWeight_i(Array2DRowRealMatrix weight_i) {
        this.weight_i = weight_i;
    }

    public Array2DRowRealMatrix getWeight_ih() {
        return weight_ih;
    }

    public void setWeight_ih(Array2DRowRealMatrix weight_ih) {
        this.weight_ih = weight_ih;
    }

    public Array2DRowRealMatrix getWeight_hh() {
        return weight_hh;
    }

    public void setWeight_hh(Array2DRowRealMatrix weight_hh) {
        this.weight_hh = weight_hh;
    }

    public Array2DRowRealMatrix getWeight_ho() {
        return weight_ho;
    }

    public void setWeight_ho(Array2DRowRealMatrix weight_ho) {
        this.weight_ho = weight_ho;
    }

    public Array2DRowRealMatrix getBias_i() {
        return bias_i;
    }

    public void setBias_i(Array2DRowRealMatrix bias_i) {
        this.bias_i = bias_i;
    }

    public Array2DRowRealMatrix getBias_ih() {
        return bias_ih;
    }

    public void setBias_ih(Array2DRowRealMatrix bias_ih) {
        this.bias_ih = bias_ih;
    }

    public Array2DRowRealMatrix getBias_hh() {
        return bias_hh;
    }

    public void setBias_hh(Array2DRowRealMatrix bias_hh) {
        this.bias_hh = bias_hh;
    }

    public Array2DRowRealMatrix getBias_ho() {
        return bias_ho;
    }

    public void setBias_ho(Array2DRowRealMatrix bias_ho) {
        this.bias_ho = bias_ho;
    }

    @Override
    public String toString() {
        return "Brain{" + "\n" +
                "weight_i=" + weight_i + "\n" +
                "weight_ih=" + weight_ih + "\n" +
                "weight_hh=" + weight_hh + "\n" +
                "weight_ho=" + weight_ho + "\n" +
                "bias_i=" + bias_i + "\n" +
                "bias_ih=" + bias_ih + "\n" +
                "bias_hh=" + bias_hh + "\n" +
                "bias_ho=" + bias_ho + "\n" +
                '}';
    }
}
