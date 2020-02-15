package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

/**
 * Клас с информацией о здании.
 */
public class Structure extends MapObject {
    private static final String[] COLORS = {"#0480B7", "#C4C4C4", "#FFDA8C", "#0094FF"};

    private int type;
    private String location;
    private String name;

    public Structure(int type, String location, String name) {
        super();
        this.type = type;
        this.location = location;
        this.name = name;
        isPolygon = true;
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
}