package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

/**
 * Клас с информацией о здании.
 */
public class Structure extends MapObject {
    public static final float[][] COLORS = {
            {1/255f, 128/255f, 182/255f},
            {196/255f, 196/255f, 196/255f},
            {255/255f, 217/255f, 142/255f},
            {0/255f, 148/255f, 255/255f}};

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
    public float[][] getColor() {
        return COLORS;
    }
}