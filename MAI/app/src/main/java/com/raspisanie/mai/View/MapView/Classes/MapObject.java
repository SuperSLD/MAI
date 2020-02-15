package com.raspisanie.mai.View.MapView.Classes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapObject {
    protected ArrayList<Integer> x;
    protected ArrayList<Integer> y;

    protected boolean isPolygon = false;
        
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

    /**
     * Добавление триугольников, принадлежащих
     * данному объекту, если он является многоугольником,
     * в списов вершин.
     * @return
     */
    public void addTriangles(ArrayList<Float> v) {
        ArrayList<Triangle> triangles = new ArrayList<>();

        if (isPolygon) {
            int maxIndex = 0;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < this.x.size(); i++) {
                if (x.get(i) > max) {
                    max = x.get(i);
                    maxIndex = i;
                }
            }
            //      |i  j  k |
            //VxU = |x1 y1 0 | = i(y1*0-y2*0) - j(x1*0 - x2*0) + k(x1*y2- x2*y1) = x1*y2 - x2*y1
            //      |x2 y2 0 |
            boolean vectorsOrientation = vectorMiltiplicationAbs(maxIndex-1, maxIndex, maxIndex+1);

            int i = 0;
            while (x.size() > 3) {
                if (vectorMiltiplicationAbs(i, i+1, i+2) == vectorsOrientation) {
                    boolean correct = true;
                    for (int j = 0; j < x.size(); j++) {
                        if (    (x.get(j) != x.get(getI(i)) && x.get(j) != x.get(getI(i+1)) && x.get(j) != x.get(getI(i+2))) &&
                                (y.get(j) != y.get(getI(i)) && y.get(j) != y.get(getI(i+1)) && y.get(j) != y.get(getI(i+2))) &&
                                pointInTriangle(
                                x.get(getI(i)), y.get(getI(i)),
                                x.get(getI(i+1)), y.get(getI(i+1)),
                                x.get(getI(i+2)), y.get(getI(i+2)),
                                x.get(j), y.get(j))) {
                            correct = false;
                        }
                    }
                    if (correct) {
                        triangles.add(new Triangle(
                                x.get(getI(i)),      y.get(getI(i)),
                                x.get(getI(i+1)), y.get(getI(i+1)),
                                x.get(getI(i+2)), y.get(getI(i+2))));
                        int removeIndex = getI(i+1);
                        x.remove(removeIndex);
                        y.remove(removeIndex);
                    }
                }
                i++;
            }
            triangles.add(new Triangle(
                    x.get(0), y.get(0),
                    x.get(1), y.get(1),
                    x.get(2), y.get(2)));

            for (Triangle triangle : triangles) {
                System.out.println(triangle.toString());
                for (int j = 0; j < triangle.getXArray().length; j++) {
                    v.add(triangle.getXArray()[j] * 0.001f);
                    v.add(triangle.getYArray()[j] * -0.001f);
                }
            }
            Logger.getLogger("mapview").log(Level.INFO, "triangles create : " + triangles.size());
        }
    }

    /**
     * Получение индекса вершины.
     * Для упрощения проверки на соответсвия размерам списка.
     * @param i входной индекс
     * @return индекс i E [0; x,size()]
     */
    private int getI(int i){
        return i >= x.size() ? i % x.size() : i >= 0 ? i : x.size() - i;
    }

    /**
     * Определение направления вектора полученного в результате
     * векторного умножения двух сторон полигона.
     * @param p1 точка
     * @param p2 центральная точка
     * @param p3 точка
     * @return совпажает ли направление вектора с положительным направлением.
     */
    private boolean vectorMiltiplicationAbs(int p1, int p2, int p3) {
        int x1 = x.get(getI(p1)) - x.get(getI(p2));
        int y1 = y.get(getI(p1)) - y.get(getI(p2));

        int x2 = x.get(getI(p3)) - x.get(getI(p2));
        int y2 = y.get(getI(p3)) - y.get(getI(p2));
        return x1*y2 - x2*y1 >= 0;
    }

    /**Проверка на принадлежность точки к треугольнику.
     * @return true если тока в треугольнике.
     */
    private boolean pointInTriangle(int x1, int y1,
                                    int x2, int y2,
                                    int x3, int y3,
                                    int x0, int y0) {
        int p1 = (x1 - x0)*(y2 - y1) - (x2 - x1)*(y1 - y0);
        int p2 = (x2 - x0)*(y3 - y2) - (x3 - x2)*(y2 - y0);
        int p3 = (x3 - x0)*(y1 - y3) - (x1 - x3)*(y3 - y0);
        return (p1 > 0 && p2 > 0 && p3 > 0) || (p1 < 0 && p2 < 0 && p3 < 0);
    }

    /**
     * Класс для хранения данных о треугольниках.
     */
    protected class Triangle{
        private int[] x = new int[3];
        private int[] y = new int[3];

        public Triangle(int x1, int y1,
                        int x2, int y2,
                        int x3, int y3) {
            this.x[0]  = x1; this.x[1] = x2; this.x[2] = x3;
            this.y[0]  = y1; this.y[1] = y2; this.y[2] = y3;
        }

        /**
         * Получение массива вершин.
         * @return
         */
        public int[] getXArray() {
            return x;
        }

        /**
         * Получение массива вершин.
         * @return
         */
        public int[] getYArray() {
            return y;
        }

        @NonNull
        @Override
        public String toString() {
            return "[("+x[0]+";"+y[0]+"),("+x[1]+";"+y[1]+"),("+x[2]+";"+y[2]+")]";
        }
    }
}
