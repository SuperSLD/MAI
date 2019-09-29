package com.raspisanie.mai.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Day;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.Subject;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.Classes.Week;
import com.raspisanie.mai.R;

import java.util.ArrayList;

public class LoadTimeTableActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private ArrayList<Week> weeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_time_list);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
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

                //Составление списка недель.
                weeks = new ArrayList<>();
                for (int i = 0; i < weekList.length - 1; i++) {
                    int n = Integer.parseInt(
                            weekList[i].split("<tr><td >")[1].split("</td><td>")[0]);
                    String date = weekList[i].split("\">")[1];
                    String getString = "education/schedule/detail.php" +
                            weekList[i].split("\">")[0].split("href=\"")[1];
                    weeks.add(new Week(n, date));
                    System.out.println("номер недели - " + n + " / дата - " + date
                            + " / get - " + getString);

                    final int I = i + 1;
                    final int I2 = weekList.length - 1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.textViewProgress)).setText(
                                    "Загружаем ваше расписание...\n" +
                                            Integer.toString(I) + "/" + Integer.toString(I2)
                            );
                        }
                    });

                    s = null;
                    while (s == null)
                    s = url.get(getString);
                    String[] dayList = s.split("<div class=\"sc-table-col sc-day-header sc-gray\" >");
                    //Составление списка дней.
                    for (int j = 1; j < dayList.length; j++) {
                        Day day = new Day(
                                dayList[j].split("<span")[0],
                                dayList[j].split("</")[0].split(">")[1]
                        );

                        //Составление списка предметов.
                        String[] subjectList = dayList[j].split("<div class=\"sc-table-col sc-item-time\">");
                        for (int k = 1; k < subjectList.length; k++) {
                            String lect = "";
                            try {
                                lect = subjectList[k].split("<span class=\"sc-lecturer\">")[1].split("<")[0];
                            } catch (Exception ex) {
                            }

                            Subject subject = new Subject(
                                    subjectList[k].split(" ")[0] + " - " +
                                            subjectList[k].split("<")[0].split(" ")[2],
                                    subjectList[k].split("table-col sc-item-type\">")[1].split("<")[0],
                                    subjectList[k].split("<span class=\"sc-title\">")[1].split("<")[0],
                                    lect,
                                    subjectList[k].split("marker\">&nbsp;</span>")[1].split("</div>")[0]
                            );
                            day.addSubject(subject);
                        }

                        weeks.get(i).addDay(day);
                    }

                }

                Gson gson = new Gson();
                String json = gson.toJson(weeks);

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("weeks", json);
                editor.apply();

                Intent intent = new Intent(LoadTimeTableActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).start();
    }
}
