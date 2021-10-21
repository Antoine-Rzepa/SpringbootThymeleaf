package com.springbootthymelead.springbootthymelead.model;

public class Personnage {

    private int id;
    private String name;
    private String type;

    public Personnage(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Personnage(){

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "personnages{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
