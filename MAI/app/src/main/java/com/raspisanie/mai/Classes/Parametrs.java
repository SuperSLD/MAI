package com.raspisanie.mai.Classes;

import java.util.HashMap;

/**
 * Хранение обхектов для передачи между активностями.
 */
public class Parametrs {
    public static final String SERVER_IP = "https://jutter.online:433/mai_web/";

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

    /**
     * Получение целого числа.
     * @param key ключ.
     * @return целое чсло.
     */
    public static int getInt(String key) {
        return (int) parametrs.get(key);
    }

    /**
     * Получение строки.
     * @param key ключ.
     * @return строка.
     */
    public static String getSrtring(String key) {
        return (String) parametrs.get(key);
    }
}