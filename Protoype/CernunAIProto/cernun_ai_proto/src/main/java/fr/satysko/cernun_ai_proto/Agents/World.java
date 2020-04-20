package fr.satysko.cernun_ai_proto.Agents;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class World {
    int rows;
    int cols;

    double[][] rewards;

    public World(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        rewards = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rewards[i][j] = 0;
            }
        }
        for (int i = 0; i < 15; i++) {
            int idxX = (int) random(rows);
            int idxY = (int) random(cols);
            if (idxX == rows -1 && idxY == cols - 1) {
                idxX --;
            }
            rewards[idxX][idxY] = -1;
        }
        for (int i = 0; i < 5; i++) {
            int idxX = (int) random(rows);
            int idxY = (int) random(cols);
            if (idxX == rows -1 && idxY == cols - 1) {
                idxX --;
            }
            rewards[idxX][idxY] = 1;
        }
        rewards[4][3] = 1;
    }

    public double[][] getView(int posX, int posY, int size){
        double[][] res = new double[2 * size + 1][2 * size + 1];
        for (int dx = - size ; dx <= size ; dx ++) {
            for (int dy = - size ; dy <= size ; dy ++) {
                int idxX = ((posX + dx) % rows) >= 0 ? (posX + dx) % rows : rows + (posX + dx) % rows;
                int idxY = ((posY + dy) % cols) >= 0 ? (posY + dy) % cols : cols + (posY + dy) % cols;
                res[dx + size][dy + size] = rewards[idxX][idxY];
            }
        }
        return res;
    }

    public double getRewards(int posX, int posY){
        return rewards[posX][posY];
    }

}
