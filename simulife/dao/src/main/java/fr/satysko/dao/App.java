package fr.satysko.dao;

import fr.satysko.models.User;
import fr.satysko.models.World;

public class App 
{

    public static void main( String[] args ) {
        GenericDAO<World> worldDAO = new GenericDAO<>(Constantes.PERSISTENCE_UNIT_NAME, World.class);

        worldDAO.close();
    }
}
