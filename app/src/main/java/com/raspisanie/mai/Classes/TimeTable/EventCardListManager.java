package com.raspisanie.mai.Classes.TimeTable;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.URLSendRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                eventCards.add(new EventCard(ev.getName(), ev.getDate(), bytes[i], ev.getInfo()));
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

                String info = null;
                while (info == null) {
                    String urlString =
                            "/press/events/detail.php?" +
                                    eventsHtml[i].split("<h5><a href=\"/press/events/detail\\.php\\?")[1].split("\">")[0];
                    Logger.getLogger("mailog").log(Level.INFO, "INFORM " + urlString);
                    info = url.get(urlString);
                }
                info = info.split("<div class=\"text text-lg\">")[1].split("</div>")[0];

                events.add(new EventCard(eventName, eventDate, encoded, informationConvert(info)));
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger("mailog").log(Level.INFO, "event card list updater error");
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
        editor.putString("lastUpdateEvents",
                new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
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
     * @param list список объектов недели.
     */
    public static void insertEventCardsInList(ArrayList<Object> list, int week) {
        for (EventCard event :eventCards) {
            boolean insert = false;
            String[] eventDate = {event.getDate().split(" ")[0], getM(event.getDate().split(" ")[1])};
            for (int i = 0; i < list.size(); i++) if (list.get(i) instanceof Day) {
                Logger.getLogger("mailog").log(Level.INFO, "EventCardListManager event date:"
                        + event.getDate().split(" ")[0] + "."
                        + getM(event.getDate().split(" ")[1]) + " //day date:"
                        + ((Day) list.get(i)).getDate());
                String[] dayDate   = ((Day) list.get(i)).getDate().split("\\.");
                if (dayDate[0].equals(eventDate[0])
                        && dayDate[1].equals(eventDate[1])) {
                    int dayPosition = list.indexOf(list.get(i));
                    if (dayPosition == list.size()-1) {
                        list.add(event);
                    } else {
                        list.add(dayPosition + 1, event);
                    }
                    insert = true;
                }
            }
            if ( isThisWeek(eventDate, week) && !insert){
                list.add(event);
            }
        }
    }

    /**
     * Номер месяца по его сокращенному названию.
     */
    private static String getM(String m) {
        String[] M = {
                "янв", "фев", "апр",
                "мар", "май", "июн",
                "июл", "авг", "сен",
                "окт", "ноя", "дек"};
        for (int i = 0; i < M.length; i++)
            if (M[i].equals(m)) return Integer.toString(i+1);
        return Integer.toString(1);
    }

    /**
     * Проверка принадлежности события к текущей недели.
     * @param dateString
     * @return
     */
    private static boolean isThisWeek(String[] dateString, int week) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            String s = dateString[0] + "." + dateString[1] + "."
                    + Calendar.getInstance().get(Calendar.YEAR);
            date = ft.parse(s);
        } catch (Exception ex) { ex.printStackTrace(); }
        Date top = null;
        Date down = null;
        try {
            top = ft.parse(((Week[])Parametrs.getParam("weeks"))[week].getDate().split(" - ")[1]);
            down = ft.parse(((Week[])Parametrs.getParam("weeks"))[week].getDate().split(" - ")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ((!date.before(down) && date.before(top)))
                || (date.compareTo(top) == 0 || date.compareTo(down) == 0);
    }

    /**
     * Преобоазование текста информации в читаемый вид.
     * @return
     */
    private static String informationConvert(String inf){
        Document jsoupDoc = Jsoup.parse(inf);

        //set pretty print to false, so \n is not removed
        jsoupDoc.outputSettings(new Document.OutputSettings().prettyPrint(false));

        //select all <br> tags and append \n after that
        jsoupDoc.select("br").after("\\n");

        //select all <p> tags and prepend \n before that
        jsoupDoc.select("p").before("\\n");

        //get the HTML from the document, and retaining original new lines
        String str = jsoupDoc.html().replaceAll("\\\\n", "\n");

        String strWithNewLines =
                Jsoup.clean(str, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        return strWithNewLines.replaceAll("&nbsp;", " ")
                .replaceAll("  ", " ")
                .replaceAll("\n\n", "\n")
                .replaceAll("\n\n", "\n");
    }
}