package fr.satysko.dao;

import fr.satysko.models.User;

public class App 
{

    public static void main( String[] args ) {
        GenericDAO<User> userDAO = new GenericDAO<>(Constantes.PERSISTENCE_UNIT_NAME, User.class);

        userDAO.close();
    }
}
