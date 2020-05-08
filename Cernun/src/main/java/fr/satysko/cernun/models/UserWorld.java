package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"world_id", "user_id"}))
public class UserWorld extends Entite {
    @ManyToOne
    private World world;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "userWorld", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
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

    @Override
    public String toString() {
        return "UserWorld{" +
                "world=" + world +
                ", user=" + user +
                '}';
    }
}
