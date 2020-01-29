package fr.satysko.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {
    // La connexion n'est accessible qu'aux classes dérivées : les DAO
    protected Connection connection = null;

    /**
     * Constructeur
     * @param connection Contient l'instance unique(singleton) de l'objet Connection en base
     */
    public DAO(Connection connection){
        this.connection = connection;
    }

    /**
     *
     * @param obj L'entité qui va être persistée
     * @return boolean succès de la persistence
     */
    public abstract boolean create(T obj);

    /**
     *
     * @param id de l'objet à trouver
     * @return T l'enregistrement transformé en objet, une instance de T
     */
    public abstract T find(int id);

    /**
     *
     * @return instances = toutes les instances de l'objet présentes en base dans une collection de type arraylist
     */
    public abstract List<T> findAll();

    /**
     *
     * @param id de l'objet
     * @return boolean qui indique si l'opération de suppression s'est bien déroulée correctement
     */
    public abstract boolean delete(long id);

    /**
     *
     * @return booléen = résultat opération
     */
    public abstract boolean deleteAll();

    /**
     *
     * @param obj = instance à mettre à jour
     * @return booléen = résultat
     */
    public abstract boolean update(T obj);

    /**
     * Fermeture de la connexion pour éviter les problèmes
     */
    public void closeConnection() {
        try {
            System.out.println("Fermeture de la connexion BDD");
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
