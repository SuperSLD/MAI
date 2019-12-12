package com.raspisanie.mai.Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Карточка события
 */
public class EventCard {
    private String name;
    private String date;

    public EventCard(String name) {
        this.name = name;
        Logger.getLogger("mailog").log(Level.INFO, "create EventCard");
    }

    /**
     * Получение названия мероприятия.
     * @return название.
     */
    public String getName(){
        return this.name;
    }
}
