package fr.satysko.cernun.models;

import javax.persistence.Entity;

@Entity
public class Picture extends Entite {

    private String name;
    private String ipath;
    private String extension;

    public Picture(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpath() {
        return ipath;
    }

    public void setIpath(String ipath) {
        this.ipath = ipath;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "name='" + name + '\'' +
                ", ipath='" + ipath + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
