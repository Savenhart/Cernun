package fr.satysko.cernun_ai_proto.Agents;

/**
 * Creature
 */
public class Creature {

    private String name; 
    private int coordX;
    private int coordY;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void movement(int action){
        switch (action){
            case 0:
                this.coordY++;
                break;
            case 1:
                this.coordX++;
                break;
            case 2:
                this.coordY--;
                break;
            case 3:
                this.coordX--;
                break;
            default:
                System.out.println("No more movement");
        }

    }

}