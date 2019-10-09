package com.raspisanie.mai.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raspisanie.mai.Classes.Parametrs;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.Classes.URLSendRequest;
import com.raspisanie.mai.R;

public class LoadInformationActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private SimpleTree<String> sport = new SimpleTree<>("Виды спорта");
    private SimpleTree<String> creative = new SimpleTree<>("Творческие коллективы");
    private SimpleTree<String> studOrg = new SimpleTree<>("Студенческие органмзации");

    private final String COUNT = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_information);

        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        new Thread(() -> {
            URLSendRequest url;
            url = new URLSendRequest("https://mai.ru/", 50000);

            /*
            Загрузка данных о спортивных секциях
             */
            String s = null;
            while (s == null)
                s = url.get("life/sport/sections.php");

            String[] korp = s.split("<th colspan=\"3\">");
            for (int i = 1; i < korp.length; i++) {
                SimpleTree<String> korpTree = new SimpleTree<>(
                        korp[i].split("</")[0].replaceAll("\t", "")
                                .replaceAll("&nbsp;", " ")
                                .replaceAll("\n", "")
                                .replaceAll("<p>", "")
                );

                String[] group = korp[i].split("<tr>");
                for (int j = 1; j < group.length; j++) {
                    try {
                        SimpleTree<String> sectTree = new SimpleTree<>(
                                deleteHTML(group[j].split("</td>")[0].split("<td>")[1])
                                + "<!>" + deleteHTML(group[j].split("</td>")[1].split("<td>")[1])
                                + "<!>" + deleteHTML(group[j].replaceAll("<!", "</")
                                        .replaceAll("<a", "</").split("</")[2].split("<td>")[1])
                        );
                        korpTree.addChild(sectTree);
                    } catch (IndexOutOfBoundsException ex) {
                        //ex.printStackTrace();
                    }
                }

                sport.addChild(korpTree);
            }
            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n1/" + COUNT);

            /*
            Загрузка данных о спортивных секциях
             */
            s = null;
            while (s == null)
                s = url.get("life/create/dkit/kollektivy-dkit.php");
            String[] group = s.split("<p>")[1].split("<b>");

            for (int i = 1; i < group.length; i++) {
                try {
                    SimpleTree<String> groupTree = new SimpleTree<>(deleteHTML(
                            group[i].split("</b>")[0]
                            + "<!>" + group[i].split("<br>")[1]
                            + "<!>" + group[i].split("<br>")[3]
                    ));

                    creative.addChild(groupTree);
                } catch (IndexOutOfBoundsException ex) {
                    //ex.printStackTrace();
                }
            }

            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n2/" + COUNT);

            /*
            Загрузка данных о студенческих организациях
            */
            s = null;
            while (s == null)
                s = url.get("life/join/index.php");

            String[] org = s.split("<th colspan=");

            for (int i = 1; i < group.length; i++) {
                try {
                    SimpleTree<String> orgTree = new SimpleTree<>(deleteHTML(
                            org[i].replaceAll("<br>", "").split("</")[0].split(">")
                                    [org[i].split("</")[0].split(">").length-1]
                    ));

                    studOrg.addChild(orgTree);
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }

            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n3/" + COUNT);
            System.out.println(studOrg.toString(0));

            Gson gson = new Gson();
            Parametrs.setParam("sport", sport);
            Parametrs.setParam("creative", creative);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("sport", gson.toJson(sport));
            editor.putString("creative", gson.toJson(creative));
            editor.apply();

            Intent intent = new Intent(LoadInformationActivity.this, MainActivity.class);
            startActivity(intent);
        }).start();
    }

    /**
     * Установка текста на TextView под прогрес баром.
     * @param s текст который будет под прогрес баром.
     */
    private void setProgressText(String s) {
        runOnUiThread(() -> ((TextView) findViewById(R.id.textViewProgress)).setText(s));
    }

    /**
     * Удаление из строки лишних отступов и переноса строки.
     * @param s
     * @return
     */
    private String deleteHTML(String s) {
        return s.replaceAll("\t", "")
                .replaceAll("&nbsp;", " ")
                .replaceAll("\n", "")
                .replaceAll("<br>", "");
    }
}