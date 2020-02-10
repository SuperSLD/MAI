package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

/**
 * Участки с газонами.
 */
public class GrassZone {
    public static final String COLOR = "#C4EFC7";

    private ArrayList<Integer> x;
    private ArrayList<Integer> y;

    public GrassZone() {
        x = new ArrayList<>();
        y = new ArrayList<>();
    }

    /**
     * добавление точки здания
     * @param x
     * @param y
     */
    public void addVector(int x, int y) {
        this.x.add(x);
        this.y.add(y);
    }

    /**
     * получение списка координат
     */
    public ArrayList<Integer> getX() {
        return x;
    }

    /**
     * получение списка координат
     */
    public ArrayList<Integer> getY() {
        return y;
    }
}
