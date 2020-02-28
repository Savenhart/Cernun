package fr.satysko.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Appartenance extends Entite {
    @ManyToOne
    private World world;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "appartenance", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Creature> creatures;

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

    public Set<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(Set<Creature> creatures) {
        this.creatures = creatures;
    }

}
