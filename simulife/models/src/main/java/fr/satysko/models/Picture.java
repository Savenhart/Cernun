package fr.satysko.models;

public class Picture {
    private int id;
    private String name;
    private String ipath;
    private String type;

    public Picture() {
    }

    public Picture(String type) {

    }

    public Picture(int id, String name, String ipath) {
        this.id = id;
        this.name = name;
        this.ipath = ipath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
