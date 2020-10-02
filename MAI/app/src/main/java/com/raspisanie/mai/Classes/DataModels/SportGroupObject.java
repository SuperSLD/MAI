package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.SplittableRandom;

/**
 * Хранение данных о спортивных секциях.
 */
public class SportGroupObject {
    private String name;
    private ArrayList<SportSection> sportSections;

    /**
     * Хранение отдельной секции.
     */
    public static class SportSection {
        private String name;
        private String administrator;
        private String phoneNumber;

        public SportSection(String name, String administrator, String phoneNumber) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            if (this.getName().charAt(0) == ' ') {
                this.name = this.getName().replaceFirst(" ", "");
            }
            this.administrator = administrator;
        }

        public String getName() {
            return name;
        }

        public String getAdministrator() {
            return administrator;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @NonNull
        @Override
        public String toString() {
            return name + administrator + phoneNumber;
        }
    }

    public SportGroupObject(String name) {
        this.sportSections = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addSection(String name, String administrator, String phoneNumber) {
        sportSections.add(new SportSection(name, administrator, phoneNumber));
    }

    public ArrayList<SportSection> getSportSections() {
        return sportSections;
    }

    @NonNull
    @Override
    public String toString() {
        return name + sportSections.toString();
    }
}
