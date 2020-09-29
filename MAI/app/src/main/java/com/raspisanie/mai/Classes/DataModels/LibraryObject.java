package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Обхект библиотеки.
 */
public class LibraryObject {
    private String name;
    private ArrayList<String> sections;

    public LibraryObject(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addItem(String section) {
        sections.add(section);
    }

    public ArrayList<String> getSections() {
        return sections;
    }

    @NonNull
    @Override
    public String toString() {
        return name + sections.toString();
    }
}
