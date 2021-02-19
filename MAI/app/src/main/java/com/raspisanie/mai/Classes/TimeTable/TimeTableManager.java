package com.raspisanie.mai.Classes.TimeTable;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.RealmModels.DayModel;
import com.raspisanie.mai.Classes.RealmModels.SubjectModel;
import com.raspisanie.mai.Classes.RealmModels.WeekModel;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.URLSendRequest;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * @author Леонид Соляной (solyanoy.leonid@gmail.com)
 *
 * Обновление и загрузка расписания а также проверка.
 */
public class TimeTableManager {

    private int weekListSize;
    private int position;
    private boolean isNewWeekList = false;

    private boolean isLoad = false;

    private ArrayList<Week> weeks;
    private int thisWeek;

    private static TimeTableManager timeTableManager;

    private TimeTableManager() {
        this.weeks = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<WeekModel> realmResults = realm.where(WeekModel.class).sort("id").findAll();

        for (WeekModel weekModel : realmResults) {
            Week week = new Week(weekModel.n, weekModel.date);
            for (DayModel dayModel : weekModel.days) {
                Day day = new Day(dayModel.date, dayModel.name);
                for (SubjectModel subjectModel : dayModel.subjects) {
                    Subject subject = new Subject(
                            subjectModel.time,
                            subjectModel.type,
                            subjectModel.name,
                            subjectModel.lecturer,
                            subjectModel.place
                    );
                    day.addSubject(subject);
                }
                week.addDay(day);
            }
            weeks.add(week);
        }
        realm.close();
    }

    public static void init() {
        if (timeTableManager == null) {
            timeTableManager = new TimeTableManager();
        }
    }

    /**
     * Получение текущего одиночного объекта.
     * @return объект.
     */
    public static TimeTableManager getInstance() {
        return timeTableManager;
    }

    /**
     * @author Леонид Соляной (solyanoy.leonid@gmail.com)
     *
     * Обновление расписания.
     * @param mSettings SharedPreferences.
     * @return если обновлено true.
     */
    public boolean update(SharedPreferences mSettings) {
        try {
            isLoad = false;
            isNewWeekList = false;
            weekListSize = 0;
            position = 0;

            ArrayList<Week> weeks;

            String dateWeek = null;
            try {
                dateWeek = ((Week[]) Parametrs.getParam("weeks"))[0].getDate();
            } catch (NullPointerException ex) {
                dateWeek = "none";
            }

            URLSendRequest url;
            url = new URLSendRequest("https://mai.ru/", 50000);

            String s = null;
            while (s == null)
                s = url.get("education/schedule/detail.php?group=" +
                        ((SimpleTree<String>) Parametrs.getParam("tree")).getChildList()
                                .get(mSettings.getInt("kurs", -1)).getChildList()
                                .get(mSettings.getInt("fac", -1)).getChildList()
                                .get(mSettings.getInt("group", -1)).getValue());

            s = s.split("<table class=\"table\" >")[1];
            String[] weekList = s.split("</table><br>")[0].split("</td>\n");

            weekListSize = weekList.length - 1;
            Logger.getLogger("mai_time_table").log(Level.INFO, "weekListSize = " + weekListSize);

            //Составление списка недель.
            Realm realm = Realm.getDefaultInstance();
            weeks = new ArrayList<>();

            String firstString = "";

            for (int i = 0; i < weekList.length - 1; i++) {
                int n = Integer.parseInt(
                        weekList[i].split("<tr><td >")[1].split("</td><td>")[0]);
                String data = Jsoup.parse(weekList[i].split("</td><td>")[1]).text();
                if (firstString.length() < 3) {
                    firstString = "education/schedule/detail.php" +
                            weekList[i].split("\">")[0].split("href=\"")[1];
                    firstString = firstString.split("week=")[0] + "week=";
                }
                Week week = new Week(n, data);
                Logger.getLogger("mailog").log(Level.INFO, "номер недели - " + (i+1) + " / дата - " + data
                        + " / get - " + firstString + (i+1));

                final int I = i + 1;
                final int I2 = weekList.length - 1;

                s = null;
                while (s == null)
                    s = url.get(firstString + (i+1));
                String[] dayList = s.split("<div class=\"sc-table-col sc-day-header");
                //Составление списка дней.
                for (int j = 1; j < dayList.length; j++) {
                    Day day = new Day(
                            dayList[j].split("<span")[0].split(">")[1],
                            dayList[j].split("<span class=\"sc-day\">")[1].split("</")[0]
                    );

                    //Составление списка предметов.
                    String[] subjectList = dayList[j].split("<div class=\"sc-table-col sc-item-time\">");
                    for (int k = 1; k < subjectList.length; k++) {
                        String time = "";
                        String type = "";
                        String name = "";
                        String lecturer = "";
                        String location = "";

                        try {
                            time = subjectList[k].split("</div>")[0];
                            time = Jsoup.parse(time).text();//.replaceAll("&ndash;", "-");;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            type = subjectList[k].split("<div class=\"sc-table-col sc-item-type\">")[1].split("</div>")[0];
                            type = Jsoup.parse(type).text();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            name = subjectList[k].split("<span class=\"sc-title\">")[1].split("</span>")[0];
                            name = Jsoup.parse(name).text();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            lecturer = subjectList[k].split("<span class=\"sc-lecturer\">")[1].split("</span")[0];
                            lecturer = Jsoup.parse(lecturer).text();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            location = subjectList[k].split("<div class=\"sc-table-col sc-item-location\">")[1].split("</div>")[0]
                                    .replaceAll("<br>", " - ");
                            location = Jsoup.parse(location).text();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        Subject subject = new Subject(
                                time,
                                type,
                                name,
                                lecturer,
                                location
                        );
                        day.addSubject(subject);
                    }

                    week.addDay(day);
                }
                position = i;
                weeks.add(week);
                Logger.getLogger("mai_time_table").log(Level.INFO, "position (loaded weeks) = " + position);
            }
            for (Week week : weeks) {
                saveWeekInRealm(realm, week, week.getN());
            }
            realm.close();
            this.weeks.clear();
            this.weeks.addAll(weeks);
            if (weeks.size() > 0) {
                if (!dateWeek.equals(weeks.get(0).getDate())) isNewWeekList = true;

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("lastUpdate",
                        new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
                editor.apply();
                isLoad = true;
                MainActivity.setThisWeek();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Получение списка недель.
     * @return
     */
    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    /**
     * Получение строки прогресса.
     */
    public String getProgressString() {
        return (position + 1) + "/" + (weekListSize > 0 ? weekListSize : "???");
    }

    /**
     * Проверка окончания загрузки.
     */
    public boolean isLoad() {
        return isLoad;
    }

    /**
     * Установка статуса загрузки.
     */
    public void setLoadStatus(boolean load) {
        isLoad = load;
    }

    /**
     * Проверка на наличие нового расписания.
     * @return
     */
    public boolean isNewWeekList() {
        return isNewWeekList;
    }

    /**
     * Получение текущей недели.
     * @return номер текущей недели.
     */
    public int getThisWeek() {
        return thisWeek;
    }

    /**
     * Установка текущей недели.
     * @param thisWeek номер текущей недели.
     */
    public void setThisWeek(int thisWeek) {
        this.thisWeek = thisWeek;
    }

    /**
     * Сохранение недели в реалме.
     * @param realm реалм объект.
     * @param week сохраняемая неделя.
     */
    private void saveWeekInRealm(Realm realm, Week week, int count) {
        realm.beginTransaction();
        WeekModel weekModel = new WeekModel();
        weekModel.id = count;
        weekModel.n = week.getN();
        weekModel.date = week.getDate();
        weekModel.days = new RealmList<>();
        for (Day day : week.getDaysList()) {
            DayModel dayModel = new DayModel();
            dayModel.date = day.getDate();
            dayModel.name = day.getName();
            dayModel.subjects = new RealmList<>();
            for (Subject subject : day.getSubjectList()) {
                SubjectModel subjectModel = new SubjectModel();
                subjectModel.lecturer = subject.getLecturer();
                subjectModel.name = subject.getName();
                subjectModel.place = subject.getPlace();
                subjectModel.time = subject.getTime();
                subjectModel.type = subject.getType();

                dayModel.subjects.add(subjectModel);
            }

            weekModel.days.add(dayModel);
        }
        realm.insertOrUpdate(weekModel);
        realm.commitTransaction();
    }
}
