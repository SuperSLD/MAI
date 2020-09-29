package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.NonNull;

/**
 * Хранение данных о студенческих группах.
 */
public class StudentGroupObject {
    private String name;
    private String administrator;
    private String phoneNumber;
    private String information;

    public StudentGroupObject(String name,
                              String administrator,
                              String phoneNumber,
                              String information) {
        this.name = name;
        this.administrator = administrator;
        this.phoneNumber = phoneNumber;
        this.information = information;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdministrator() {
        return administrator;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    @NonNull
    @Override
    public String toString() {
        return name + administrator + phoneNumber + information;
    }
}
