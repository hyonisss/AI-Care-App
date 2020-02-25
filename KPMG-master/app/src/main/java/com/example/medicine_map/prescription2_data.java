package com.example.medicine_map;

public class prescription2_data {
    private String date, hospital, disease, disease_date;
    private int profile, clock;


    public prescription2_data(String date, String hospital, String disease, String disease_date, int profile, int clock) {
        this.date = date;
        this.hospital = hospital;
        this.disease = disease;
        this.disease_date = disease_date;
        this.profile = profile;
        this.clock = clock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDisease_date() {
        return disease_date;
    }

    public void setDisease_date(String disease_date) {
        this.disease_date = disease_date;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }
}
