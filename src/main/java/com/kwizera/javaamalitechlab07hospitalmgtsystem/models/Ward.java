package com.kwizera.javaamalitechlab07hospitalmgtsystem.models;

public class Ward {
    private int id;
    private String name;
    private Department department;
    private Nurse supervisor;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Nurse getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Nurse supervisor) {
        this.supervisor = supervisor;
    }
}
