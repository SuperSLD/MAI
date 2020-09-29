package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.NonNull;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CanteenObject {
    private int id;
    private String name;
    private String date;
    private String place;

    public CanteenObject(int id, String name, String place, String date) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.place = place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    @NonNull
    @Override
    public String toString() {
        return id + name + date + place;
    }
}
