package com.raspisanie.mai.Classes.TimeTable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
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
    private String encodedBitmap;
    private String info;
    private boolean delete = false;

    private static int eventCardCount = 0;
    private int eventCardID;

    public EventCard(String name, String date, String bitmap, String info, boolean delete) {
        this.name = name;
        this.date = date;
        this.encodedBitmap = bitmap;
        byte[] imageAsBytes = Base64.decode(bitmap.getBytes(), Base64.DEFAULT);
        this.bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        eventCardCount++;
        this.eventCardID = eventCardCount;
        Logger.getLogger("mailog").log(Level.INFO, "create EventCard " + date + " ID: " + eventCardID);
        this.info = info;
        this.delete = delete;
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

    /**
     * Получение информации.
     * @return
     */
    public String getInfo() {
        return info;
    }

    /**
     * Получение информации о состоянии карточки.
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * восстановление карточек.
     * @param isDelete (true если карточка скрыта).
     */
    public void setDeleteState(boolean isDelete) {
        this.delete = isDelete;
        EventCardListManager.getInstance().saveCardState(this);
    }

    /**
     * Получение закодированного изображения, для сохранения в бд.
     * @return закодированный bitmap.
     */
    public String getEncodedBitmap() {
        return encodedBitmap;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof EventCard) {
            EventCard eventCard = (EventCard) obj;
            return this.name.equals(eventCard.getName()) && this.info.equals(eventCard.getInfo());
        } else return false;
    }
}