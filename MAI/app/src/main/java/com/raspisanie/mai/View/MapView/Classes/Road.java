package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

public class Road {
    private static final String[] COLORS = {"#DBDBDB", "#FFFFFF", "#FFFFFF", "#FFF4C4"};
    private static final int[] SIZE = {4, 10, 20, 25};
    private static final boolean[] DASH = {true, false, false, false};

    private ArrayList<Integer> x;
    private ArrayList<Integer> y;

    private int type;

    public Road(int type) {
        this.type = type;

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
     * Получение цвета в зависимости от типа.
     * @return
     */
    public String getColor() {
        return COLORS[type];
    }

    /**
     * Получение размера в зависимости от типа.
     * @return
     */
    public int getSize() {
        return SIZE[type];
    }

    public boolean getDASH() {
        return DASH[type];
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
