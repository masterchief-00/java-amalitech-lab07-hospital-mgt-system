package com.kwizera.javaamalitechlab07hospitalmgtsystem.models;

import java.time.LocalDate;

public class Admission {
    private int id;
    private Hospitalization hospitalization;
    private Ward ward;
    private Bed bed;
    private LocalDate admittedAt;
    private LocalDate dischargedAt;
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hospitalization getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(Hospitalization hospitalization) {
        this.hospitalization = hospitalization;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public LocalDate getAdmittedAt() {
        return admittedAt;
    }

    public void setAdmittedAt(LocalDate admittedAt) {
        this.admittedAt = admittedAt;
    }

    public LocalDate getDischargedAt() {
        return dischargedAt;
    }

    public void setDischargedAt(LocalDate dischargedAt) {
        this.dischargedAt = dischargedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
