package com.raspisanie.mai.Classes.TimeTable;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.URLSendRequest;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Управление списком событитий.
 */
public class EventCardListManager {

    public static ArrayList<EventCard> eventCards = new ArrayList<>();

    /**
     * Инициализация списка.
     */
    public static void initList(SharedPreferences mSettings) {
        try {
            Logger.getLogger("mailog").log(Level.INFO, "init events list");
            String json = mSettings.getString("events", "");
            Gson gson = new Gson();
            EventCard[] list = gson.fromJson(json, EventCard[].class);
            String[] bytes = gson.fromJson(mSettings.getString("eventsBitmap", ""), String[].class);
            Logger.getLogger("mailog").log(Level.INFO, "events: " + list.length + "/ bytes:" + bytes.length);
            int i = 0;
            for (EventCard ev : list) {
                eventCards.add(new EventCard(ev.getName(), ev.getDate(), bytes[i]));
                Logger.getLogger("mailog").log(Level.INFO,
                        "bitmap["+i+"/"+(list.length-1)+"]: " + (ev.getBitmap() != null));
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Обновление списка.
     * @param mSettings
     */
    public static void eventCardListUpdate(SharedPreferences mSettings) {
        ArrayList<EventCard> events = new ArrayList<>();
        ArrayList<String> bitmapStrings = new ArrayList<>();
        URLSendRequest url;
        url = new URLSendRequest("https://mai.ru", 50000);

        String s = null;
        while (s == null)
            s = url.get("/press/events/");

        String[] eventsHtml = s.split("<div class=\"row j-marg-bottom\"");
        for (int i = 1; i < eventsHtml.length; i++) {
            try {
                String eventName = eventsHtml[i].split("<h5><a href=\"")[1].split(">")[1].split("</a")[0]
                        .replaceAll("&nbsp;", " ")
                        .replaceAll("&mdash;", " ")
                        .replaceAll("&raquo;", "\"")
                        .replaceAll("&laquo;", "\"");
                String eventDate = eventsHtml[i].split("<p class=\"b-date\">")[1].split("</p>")[0]
                        .replaceAll("&mdash;", "-");

                String urlImage = "https://mai.ru" + eventsHtml[i].split("class=\"img-responsive\" src=\"")[1].split("\"></")[0];
                Logger.getLogger("mailog").log(Level.INFO, "url:" + urlImage);
                URL newurl = new URL(urlImage);
                Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                bitmap = getResizedBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                bitmapStrings.add(encoded);
                events.add(new EventCard(eventName, eventDate, encoded));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        eventCards.clear();
        eventCards.addAll(events);
        Logger.getLogger("mailog").log(Level.INFO, "EventCardListManager load end");

        //сохранение
        Gson gson = new Gson();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("events", gson.toJson(eventCards));
        editor.putString("eventsBitmap", gson.toJson(bitmapStrings));
        editor.apply();
    }

    /**
     * Изменение размеров bitmap.
     * @param bm входной bitmap.
     * @param newWidth конечная ширина.
     * @param newHeight конечная высота.
     */
    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * Вставка карточек событий в текущую неделю.
     * @param day список объектов недели.
     */
    public static void insertEventCardsInList(ArrayList<Object> day) {

    }
}
