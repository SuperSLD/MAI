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
    private SimpleTree<String> stolTree = new SimpleTree<>("Столовые");
    private SimpleTree<String> libTree = new SimpleTree<>("Библиотеки");

    private final String COUNT = "5";

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

            setProgressText("Загружаем другую информацию о ВУЗе...\n2/" + COUNT);

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
                                    [org[i].split("</")[0].split(">").length - 1]
                                    + "<!>" + org[i].split("<td valign=\"top\">")[1].split("</td>")[0]
                                    + "<!>" + org[i].split("<td colspan=\"2\" valign=\"top\">")[1].split("</td>")[0]
                                    + "<!>" + org[i].split("<td>")[1].split("</td>")[0]
                    ));

                    studOrg.addChild(orgTree);
                } catch (IndexOutOfBoundsException ex) {
                    //ex.printStackTrace();
                }
            }

            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n3/" + COUNT);

            /*
            Загрузка данных о столовых
            */
            s = null;
            while (s == null)
                s = url.get("common/campus/cafeteria/");
            String[] stol = s.split("<tr>");

            for (int i = 2; i < stol.length - 1; i++) {
                try {
                    String[] stolSpl = stol[i].split("<p>");
                    SimpleTree<String> stolChild = new SimpleTree<>(deleteHTML(
                            stolSpl[1]
                                    + "<!>" + stolSpl[2]
                                    + "<!>" + stolSpl[3]
                                    + "<!>" + stolSpl[4]
                                    + "<!>" + stol[i].split("</tr>")[0].split("<td>")[4]
                    ));

                    stolTree.addChild(stolChild);
                } catch (IndexOutOfBoundsException ex) {
                    //ex.printStackTrace();
                }
            }

            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n4/" + COUNT);

             /*
            Загрузка данных о библиотеках
            */
            s = null;
            while (s == null)
                s = url.get("common/campus/library/");
            String[] lib = s.split("<th colspan=\"2\">");

            for (int i = 1; i < lib.length; i++) {
                try {
                    SimpleTree<String> otdel = new SimpleTree<>(
                        deleteHTML(lib[i].split("</th>")[0])
                    );
                    String[] libs = lib[i].split("<tr>");
                    for (int j = 2; j < libs.length; j++) {
                        try {
                            SimpleTree<String> room = new SimpleTree<>(deleteHTML(
                                    libs[j].split("<td>")[1].split("</td")[0]
                                    + "<!>" + libs[j].split("<td>")[2].split("</td")[0]
                                            .replaceAll("<sup>", " - ")
                            ));
                            otdel.addChild(room);
                        } catch (IndexOutOfBoundsException ex) {}
                    }
                    libTree.addChild(otdel);
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }

            setProgressText("Загружаем другую инфрмацию о ВУЗе...\n5/" + COUNT);

            Gson gson = new Gson();
            Parametrs.setParam("sport", sport);
            Parametrs.setParam("creative", creative);
            Parametrs.setParam("studOrg", studOrg);
            Parametrs.setParam("stol", stolTree);
            Parametrs.setParam("lib", libTree);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("sport", gson.toJson(sport));
            editor.putString("creative", gson.toJson(creative));
            editor.putString("studOrg", gson.toJson(studOrg));
            editor.putString("stol", gson.toJson(stolTree));
            editor.putString("lib", gson.toJson(libTree));
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
                .replaceAll("<sup>", "")
                .replaceAll("</sup>", "")
                .replaceAll("<br>", "")
                .replaceAll("<p>", "")
                .replaceAll("</p>", "")
                .replaceAll("<td>", "")
                .replaceAll("</td>", "")
                .replaceAll("</tr>", "");
    }
}