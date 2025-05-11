package com.kwizera.javaamalitechlab07hospitalmgtsystem.models;

public class Nurse {
    private int id;
    private String rotation;
    private Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }
}
