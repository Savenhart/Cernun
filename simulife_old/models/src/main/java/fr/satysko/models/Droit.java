package fr.satysko.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Droit extends Entite {
    @ManyToOne
    private World world;
    @ManyToOne
    private User user;
    private boolean isAdmin;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
