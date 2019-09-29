package com.raspisanie.mai.Classes;

import java.util.HashMap;

/**
 * Хранение обхектов для передачи между активностями.
 */
public class Parametrs {
    private static HashMap <String, Object> parametrs = new HashMap<>();

    /**
     * Сохранение обхекта.
     * @param key ключ.
     * @param object сам объект.
     */
    public static void setParam(String key, Object object) {
        parametrs.put(key, object);
    }

    /**
     * Получение объета.
     * @param key ключ.
     * @return сохраненный объект.
     */
    public static Object getParam(String key) {
        return parametrs.get(key);
    }
}
