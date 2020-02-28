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

    private int[] typeStructureCount = new int[5];
    private int[] typeRoadCount = new int[5];
    private int grassCount = 0;

    public Map(String data) {
        structures = new ArrayList<>();
        grassZones = new ArrayList<>();
        roads = new ArrayList<>();

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
            } catch (Exception ex) {
            }
        }

        String[] dataGrass = dataObjects[1].split("@");
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
            } catch (Exception ex) {
            }
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
            } catch (Exception ex) {
            }
        }

        Logger.getLogger("mapview").log(Level.INFO, "map create " + structures.size() + " : " +
                grassZones.size() + " : " + roads.size());

        ArrayList<Float> v = new ArrayList<>();

        for (GrassZone grassZone : grassZones) {
            if (grassZone.getX().size() >= 3) {
                grassCount += grassZone.addTriangles(v);
            }
        }
        for (int i = 0; i < typeRoadCount.length; i++) {
            for (Road road: roads)
                if (road.getType() == i && road.getX().size() >= 2) {
                    typeRoadCount[i] += road.addLines(v, i);
                }
        }
        for (int i = 0; i < typeStructureCount.length; i++) {
            for (Structure structure : structures)
                if (structure.getType() == i && structure.getX().size() >= 3) {
                    typeStructureCount[i] += structure.addTriangles(v);
                }
        }

        vertices = new float[v.size()];
        for (int i = 0; i < v.size(); i++) {
            vertices[i] = v.get(i);
        }
    }

    /**
     * Получение списка вершин.
     * @return
     */
    public float[] getVertices() {
        return vertices;
    }

    /**
     * Получение количества вершин кадого типа.
     * @return
     */
    public int[] getTypeStructureCount() {
        return typeStructureCount;
    }

    /**
     * Получение количества верин по типу.
     * @return
     */
    public int[] getTypeRoadCount() {
        return typeRoadCount;
    }

    /**
     * Получение количества вершин газонов.
     * @return
     */
    public int getGrassCount() {
        return grassCount;
    }
}
