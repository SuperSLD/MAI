package com.raspisanie.mai.Classes.TimeTable;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.NewsManager;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.RealmModels.EventCardModel;
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

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Управление списком событитий.
 */
public class EventCardListManager {

    private static EventCardListManager eventCardListManager;

    private static String[] state;
    public static ArrayList<EventCard> eventCards = new ArrayList<>();
    private static SharedPreferences settings;

    private EventCardListManager(Context context) {
        try {

            settings = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE);
            Realm realm = Realm.getDefaultInstance();

            RealmResults<EventCardModel> realmResults = realm.where(EventCardModel.class).findAll();

            for (EventCardModel model : realmResults) {
                eventCards.add(new EventCard(
                        model.getName(),
                        model.getDate(),
                        model.getBitmap(),
                        model.getInfo(),
                        model.isDelete()
                ));
                Logger.getLogger("mailog").log(Level.INFO, "EventCardListManager event load from REALM ["+model.getName()+"]");
            }

            realm.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Инициализация списка.
     */
    public static void init(Context context) {
        if (eventCardListManager == null) {
            eventCardListManager = new EventCardListManager(context);
        }
    }

    /**
     * Получение текущего объекта.
     */
    public static EventCardListManager getInstance() {
        return eventCardListManager;
    }

    /**
     * Обновление списка.
     */
    public void eventCardListUpdate(SharedPreferences settings) {
        try {
            ArrayList<EventCard> events = new ArrayList<>();
            URLSendRequest url;
            url = new URLSendRequest("https://mai.ru", 50000);

            String s = null;
            while (s == null) {
                try {
                    s = url.get("/press/events/");
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            String[] eventsHtml = s.split("<div class=\"row j-marg-bottom\"");
            state = new String[eventsHtml.length];
            for (int i = 1; i < eventsHtml.length; i++) {
                state[i] = "0";
                String eventName = eventsHtml[i].split("<h5><a href=\"")[1].split(">")[1].split("</a")[0]
                        .replaceAll("&nbsp;", " ")
                        .replaceAll("&mdash;", " ")
                        .replaceAll("&raquo;", "\"")
                        .replaceAll("&laquo;", "\"");
                String eventDate = eventsHtml[i].split("<p class=\"b-date\">")[1].split("</p>")[0]
                        .replaceAll("&mdash;", "- ").replaceAll("до ", "");

                String urlImage = "https://mai.ru" + eventsHtml[i].split("class=\"img-responsive\" src=\"")[1].split("\"></")[0];
                Logger.getLogger("mailog").log(Level.INFO, "url event image:" + urlImage);
                URL newurl = new URL(urlImage);
                Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                bitmap = getResizedBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedBitmap = Base64.encodeToString(b, Base64.DEFAULT);

                String info = null;
                while (info == null) {
                    String urlString =
                            "/press/events/detail.php?" +
                                    eventsHtml[i].split("<h5><a href=\"/press/events/detail\\.php\\?")[1].split("\">")[0];
                    Logger.getLogger("mailog").log(Level.INFO, "INFORM " + urlString);
                    info = url.get(urlString);
                    Thread.sleep(1000);
                }
                info = info.split("<div class=\"text text-lg\">")[1].split("</div>")[0];

                EventCard eventCard = new EventCard(eventName, eventDate, encodedBitmap, informationConvert(info), false);
                int index = eventCards.indexOf(eventCard);
                if (index >= 0) {
                    eventCard.setDeleteState(eventCards.get(index).isDelete());
                }

                Realm realm = Realm.getDefaultInstance();
                updateEventCardModel(realm, eventCard);
                realm.close();

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("lastUpdateEvents",
                        new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
                editor.apply();
            }

            eventCards.clear();
            eventCards.addAll(events);
            Logger.getLogger("mailog").log(Level.INFO, "EventCardListManager load end");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Изменение размеров bitmap.
     * @param bm входной bitmap.
     * @param newWidth конечная ширина.
     * @param newHeight конечная высота.
     */
    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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
    public void insertEventCardsInList(ArrayList<Object> list, int week) {
        if (settings != null && !settings.getBoolean("eventCheck", true)) return;
        for (EventCard event :eventCards) {
            if (!event.isDelete()) {
                try {
                    boolean insert = false;
                    String[] eventDate = {event.getDate().split(" ")[0], getM(event.getDate().split(" ")[1])};
                    for (int i = 0; i < list.size(); i++)
                        if (list.get(i) instanceof Day) {
                            Logger.getLogger("mailog").log(Level.INFO, "EventCardListManager event date:"
                                    + eventDate[0] + "."
                                    + eventDate[1] + " //day date:"
                                    + ((Day) list.get(i)).getDate() + " name:" + event.getName());
                            String[] dayDate = ((Day) list.get(i)).getDate().split("\\.");
                            if (dayDate[0].equals(eventDate[0])
                                    && dayDate[1].equals(eventDate[1])) {
                                int dayPosition = list.indexOf(list.get(i));
                                if (dayPosition == list.size() - 1) {
                                    list.add(event);
                                } else {
                                    list.add(dayPosition + 1, event);
                                }
                                insert = true;
                            }
                        }
                    if (isThisWeek(eventDate, week) && !insert) {
                        list.add(event);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Номер месяца по его сокращенному названию.
     */
    private String getM(String m) {
        String[] M = {
                "янв", "фев", "мар",
                "апр", "май", "июн",
                "июл", "авг", "сен",
                "окт", "ноя", "дек"};
        for (int i = 0; i < M.length; i++)
            if (M[i].equals(m)) return i+1 > 9 ? Integer.toString(i+1) : "0" + (i+1);
        return Integer.toString(1);
    }

    /**
     * Проверка принадлежности события к текущей недели.
     * @param dateString
     * @return не помню что это.
     */
    private boolean isThisWeek(String[] dateString, int week) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            String s = dateString[0] + "." + dateString[1] + "."
                    + Calendar.getInstance().get(Calendar.YEAR);
            date = ft.parse(s);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        Date top = null;
        Date down = null;
        try {
            top = ft.parse((TimeTableManager.getInstance().getWeeks().get(week).getDate().split(" - ")[1]));
            down = ft.parse((TimeTableManager.getInstance().getWeeks().get(week).getDate().split(" - ")[0]));
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
    private String informationConvert(String inf){
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

    /**
     * Обнуление всех скрытых карточек.
     */
    public void unarchiveEventList() {
        Realm realm = Realm.getDefaultInstance();
        for (EventCard event : eventCards) {
            event.setDeleteState(false);
            updateEventCardModel(realm, event);
        }
        realm.close();

    }

    /**
     * Сораненик состония карточек.
     */
    public void saveCardState(EventCard eventCard) {
        Realm realm = Realm.getDefaultInstance();
        updateEventCardModel(realm, eventCard);
        realm.close();
    }

    /**
     * Обновление карточки в реалме.
     */
    private void updateEventCardModel(Realm realm, EventCard eventCard) {
        realm.beginTransaction();
        EventCardModel model = new EventCardModel();

        model.setDelete(eventCard.isDelete());
        model.setBitmap(eventCard.getEncodedBitmap());
        model.setDate(eventCard.getDate());
        model.setInfo(eventCard.getInfo());
        model.setName(eventCard.getName());
        realm.insertOrUpdate(model);

        realm.commitTransaction();

    }
}