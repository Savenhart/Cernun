package fr.satysko.cernun.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
public class User extends Entite {

    private String name;
    private String pseudo;
    private String password;

    @OneToOne
    private Picture avatar;
    @OneToMany(mappedBy = "user")
    private Set<UserWorld> userWorlds;
    @OneToMany(mappedBy = "user")
    private Set<Droit> droits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getAvatar() {
        return avatar;
    }

    public void setAvatar(Picture avatar) {
        this.avatar = avatar;
    }

    public Set<UserWorld> getUserWorlds() {
        return userWorlds;
    }

    public void setUserWorlds(Set<UserWorld> userWorlds) {
        this.userWorlds = userWorlds;
    }

    public Set<Droit> getDroits() {
        return droits;
    }

    public void setDroits(Set<Droit> droits) {
        this.droits = droits;
    }
}
