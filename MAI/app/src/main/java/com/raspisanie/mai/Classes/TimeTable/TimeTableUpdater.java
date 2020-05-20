package com.raspisanie.mai.Classes.TimeTable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.LoadInformationActivity;
import com.raspisanie.mai.Activity.LoadTimeTableActivity;
import com.raspisanie.mai.Activity.MainActivity;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.R;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Леонид Соляной (solyanoy.leonid@gmail.com)
 *
 * Обновление и загрузка расписания а также проверка.
 */
public class TimeTableUpdater {

    private int weekListSize;
    private int position;
    private boolean isNewWeekList = false;

    private boolean isLoad = false;

    /**
     * @author Леонид Соляной (solyanoy.leonid@gmail.com)
     *
     * Обновление расписания.
     * @param mSettings SharedPreferences.
     * @return если обновлено true.
     */
    public boolean update(SharedPreferences mSettings) {
        try {
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
            String[] weekList = s.split("</table><br>")[0].split("</a></td>");

            weekListSize = weekList.length - 1;
            Logger.getLogger("mai_time_table").log(Level.INFO, "weekListSize = " + weekListSize);

            //Составление списка недель.
            weeks = new ArrayList<>();
            for (int i = 0; i < weekList.length - 1; i++) {
                int n = Integer.parseInt(
                        weekList[i].split("<tr><td >")[1].split("</td><td>")[0]);
                String data = weekList[i].split("\">")[1];
                String getString = "education/schedule/detail.php" +
                        weekList[i].split("\">")[0].split("href=\"")[1];
                weeks.add(new Week(n, data));
                System.out.println("номер недели - " + n + " / дата - " + data
                        + " / get - " + getString);

                final int I = i + 1;
                final int I2 = weekList.length - 1;

                s = null;
                while (s == null)
                    s = url.get(getString);
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

                    weeks.get(i).addDay(day);
                }
                position = i;
                Logger.getLogger("mai_time_table").log(Level.INFO, "position (loaded weeks) = " + position);
            }

            if (weeks.size() > 0) {
                if (!dateWeek.equals(weeks.get(0).getDate())) isNewWeekList = true;

                Gson gson = new Gson();
                String json = gson.toJson(weeks);

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("weeks", json);
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
     * Получение строки прогресса.
     */
    public String getProgressString() {
        return (position + 1) + "/" + weekListSize;
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
}
