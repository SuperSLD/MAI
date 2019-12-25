package com.raspisanie.mai.Classes.TimeTable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Карточка события
 */
public class EventCard {

    private String name;
    private String date;
    private Bitmap bitmap;
    private String bitmapString;

    private static int eventCardCount = 0;
    private int eventCardID;

    public EventCard(String name, String date, String bitmap) {
        this.name = name;
        this.date = date;
        byte[] imageAsBytes = Base64.decode(bitmap.getBytes(), Base64.DEFAULT);
        this.bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        Logger.getLogger("mailog").log(Level.INFO, "create EventCard");
        eventCardCount++;
        this.eventCardID = eventCardCount;
    }

    /**
     * Получение названия мероприятия.
     *
     * @return название.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Получение даты мероприятия.
     *
     * @return дата.
     */
    public String getDate() {
        return date;
    }

    /**
     * Вернуть идентификатро карточки.
     * @return идентификатор.
     */
    public int getEventCardID() {
        return eventCardID;
    }

    /**
     * Получение картинки мероприятия.
     *
     * @return картинка.
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}