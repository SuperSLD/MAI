package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

public class Road extends MapObject {
    private static final String[] COLORS = {"#DBDBDB", "#FFFFFF", "#FFFFFF", "#FFF4C4"};
    private static final int[] SIZE = {4, 10, 20, 25};
    private static final boolean[] DASH = {true, false, false, false};

    private int type;

    public Road(int type) {
        super();
        this.type = type;
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
}
