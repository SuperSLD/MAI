package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

public class MapObject {
    protected ArrayList<Integer> x;
    protected ArrayList<Integer> y;
        
    public MapObject() {
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
