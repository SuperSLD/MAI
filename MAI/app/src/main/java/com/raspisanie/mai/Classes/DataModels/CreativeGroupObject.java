package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.NonNull;

/**
 * Хранение информации о творческих коллективах.
 */
public class CreativeGroupObject {
    private String name;
    private String administrator;
    private String information;

    public CreativeGroupObject(String name,
                               String administrator,
                               String information) {
        this.administrator = administrator;
        this.information = information;
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public String getName() {
        return name;
    }

    public String getAdministrator() {
        return administrator;
    }

    @NonNull
    @Override
    public String toString() {
        return name + administrator + information;
    }
}
