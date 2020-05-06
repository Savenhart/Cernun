package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Access(AccessType.FIELD)
public class User extends Entite {

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String userName;
    private String password;

    @OneToOne
    private Picture avatar;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserWorld> userWorlds;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Droit> droits;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
