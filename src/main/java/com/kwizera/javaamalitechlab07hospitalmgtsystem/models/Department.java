package com.kwizera.javaamalitechlab07hospitalmgtsystem.models;

public class Department {
    private int id;
    private String name;
    private Doctor director;

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

    public Doctor getDirector() {
        return director;
    }

    public void setDirector(Doctor director) {
        this.director = director;
    }
}
