package fr.satysko.cernun_ai_proto.Agents;

public class Prototype {

    public static void main(String[] args) {
        World world = new World(7,7);
        Agent agent = new Agent(6,6,7,7,world);
        boolean partieEnCours = true;
        int compteur = 0;

        System.out.println(agent.brain);
        while (partieEnCours){
            agent.update();
            compteur ++;
            if (compteur >=100){
                partieEnCours = false;
            }
        }
        System.out.println(agent.brain);
    }
}
