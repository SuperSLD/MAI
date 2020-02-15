package com.raspisanie.mai.View.MapView.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс хранящий обекты карты.
 */
public class Map {
    private ArrayList<Structure> structures;
    private ArrayList<GrassZone> grassZones;
    private ArrayList<Road>      roads;

    private float[] vertices;

    public Map(String data) {
        structures = new ArrayList<>();
        grassZones = new ArrayList<>();
        roads      = new ArrayList<>();

        String[] dataObjects = data.split("<<>>@");
        String[] dataStructure = dataObjects[0].split("@");
        for (int i = 0; i < dataStructure.length; i++) {
            try {
                String[] param = dataStructure[i].split(">>");

                Structure structure = new Structure(Integer.parseInt(param[0]), param[1], param[2]);

                String[] vectors = param[3].split(">");
                for (int j = 0; j < vectors.length; j++) {
                    String[] vector = vectors[j].split("\\|");
                    structure.addVector(Integer.parseInt(vector[0]), Integer.parseInt(vector[1]));
                }
                structures.add(structure);
            } catch (Exception ex) {}
        }

        String[] dataGrass= dataObjects[1].split("@");
        for (int i = 0; i < dataGrass.length; i++) {
            try {
                String[] param = dataGrass[i].split(">>");

                GrassZone grassZone = new GrassZone();

                String[] vectors = param[0].split(">");
                for (int j = 0; j < vectors.length; j++) {
                    String[] vector = vectors[j].split("\\|");
                    grassZone.addVector(Integer.parseInt(vector[0]), Integer.parseInt(vector[1]));
                }
                grassZones.add(grassZone);
            } catch (Exception ex) {}
        }

        String[] dataRoad = dataObjects[2].split("@");
        for (int i = 0; i < dataRoad.length; i++) {
            try {
                String[] param = dataRoad[i].split(">>");

                Road road = new Road(Integer.parseInt(param[0]));

                String[] vectors = param[1].split(">");
                for (int j = 0; j < vectors.length; j++) {
                    String[] vector = vectors[j].split("\\|");
                    road.addVector(Integer.parseInt(vector[0]), Integer.parseInt(vector[1]));
                }
                roads.add(road);
            } catch (Exception ex) {}
        }

        Logger.getLogger("mapview").log(Level.INFO, "map create " + structures.size()+" : "+
                grassZones.size() + " : " +roads.size());

        ArrayList<Float> v  = new ArrayList<>();

        for (Structure structure : structures) {
            structure.addTriangles(v);
        }

        vertices = new float[v.size()];
        for (int i = 0; i < v.size(); i++) {
            vertices[i] = v.get(i);
        }
    }

    /**
     * Получение списка зданий.
     * @return
     */
    public ArrayList<Structure> getStructures() {
        return structures;
    }

    /**
     * Получение списка газонов.
     * @return
     */
    public ArrayList<GrassZone> getGrassZones() {
        return grassZones;
    }

    /**
     * Получение списка дорог.
     * @return
     */
    public ArrayList<Road> getRoads() {
        return roads;
    }

    /**
     * Получение списка вершин.
     * @return
     */
    public float[] getVertices() {
        return vertices;
    }
}
