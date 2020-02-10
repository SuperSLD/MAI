package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

/**
 * Клас с информацией о здании.
 */
public class Structure {
    private static final String[] COLORS = {"#0480B7", "#C4C4C4", "#FFDA8C", "#0094FF"};

    private int type;
    private String location;
    private String name;

    private ArrayList<Integer> x;
    private ArrayList<Integer> y;

    public Structure(int type, String location, String name) {
        this.type = type;
        this.location = location;
        this.name = name;

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
     * Получение типа здания.
     * @return
     */
    public int getType() {
        return this.type;
    }

    /**
     * Получение цвета в зависимости от типа.
     * @return
     */
    public String getColor() {
        return COLORS[type];
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