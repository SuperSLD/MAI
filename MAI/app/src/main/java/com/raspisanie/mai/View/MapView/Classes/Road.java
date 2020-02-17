package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;

public class Road extends MapObject {
    public static final float[][] COLORS = {
            {220 / 255f, 220 / 255f, 220 / 255f},
            {1f, 1f, 1f},
            {1f, 1f, 1f},
            {255/255f, 245/255f, 196/255f}};
    public static final int[] SIZE = {4, 10, 20, 25};
    private static final boolean[] DASH = {true, false, false, false};

    private int type;

    public Road(int type) {
        super();
        this.isLine = true;
        this.type = type;
    }

    /**
     * Получение размера в зависимости от типа.
     * @return
     */
    public int getSize() {
        return SIZE[type];
    }

    /**
     * Получение типа дороги.
     * @return
     */
    public int getType() {
        return type;
    }

}
