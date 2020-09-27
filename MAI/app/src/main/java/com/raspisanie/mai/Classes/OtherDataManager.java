package com.raspisanie.mai.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.LoadInformationActivity;
import com.raspisanie.mai.Classes.DataModels.CanteenObject;
import com.raspisanie.mai.Classes.RealmModels.CanteenModel;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmResults;

public class OtherDataManager {
    private final String COUNT = "5";

    ArrayList<CanteenObject> canteenObjects;

    private static OtherDataManager otherDataManager;

    /**
     * Закрытый конструктор.
     */
    private OtherDataManager() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CanteenModel> canteenModels = realm.where(CanteenModel.class).findAll();
        this.canteenObjects = new ArrayList<>();
        for (CanteenModel canteenModel: canteenModels) {
            canteenObjects.add(new CanteenObject(
                    canteenModel.id,
                    canteenModel.name,
                    canteenModel.place,
                    canteenModel.date
            ));
        }

        realm.close();
    }

    /**
     * Инициализация одиночного обхекта.
     */
    public static void init() {
        if (otherDataManager == null) {
            otherDataManager = new OtherDataManager();
        }
    }

    /**
     * Получение одиночного объекта.
     * @return одиночный объект.
     */
    public static OtherDataManager getInstance() {
        return otherDataManager;
    }

    /**
     * Загрузка дополнительной информации об вузе.
     * @param context контекст LoadingInformationActivity.
     */
    public void loadInformation(Context context) {
        URLSendRequest url;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.delete(CanteenModel.class);
        realm.commitTransaction();

        url = new URLSendRequest("https://mai.ru/", 50000);
        String s = null;

            /*
            Загрузка данных о спортивных секциях
             */ /*
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
        */
        ((LoadInformationActivity) context).setProgressText("Загружаем другую инфрмацию о ВУЗе...\n1/" + COUNT);

            /*
            Загрузка данных о творческих колективах.
             */ /*
        s = null;
        while (s == null)
            s = url.get("life/create/dkit/kollektivy-dkit.php");
        String[] group = s.split("<b>");

        for (int i = 1; i < group.length; i++) {
            try {
                SimpleTree<String> groupTree = new SimpleTree<>(deleteHTML(
                        group[i].split("</b>")[0]
                                + "<!>" + group[i].split("<p>")[1].split("</p>")[0]
                                + "<!>" + group[i].split("<p>")[2].split("</p>")[0]
                ));

                creative.addChild(groupTree);
            } catch (IndexOutOfBoundsException ex) {
                //ex.printStackTrace();
            }
        } */

        ((LoadInformationActivity) context).setProgressText("Загружаем другую информацию о ВУЗе...\n2/" + COUNT);

            /*
            Загрузка данных о студенческих организациях
            */ /*
        s = null;
        while (s == null)
            s = url.get("life/join/index.php");

        String[] org = s.split("<th colspan=");

        for (int i = 1; i < org.length; i++) {
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
                ex.printStackTrace();
            }
        } */
        ((LoadInformationActivity) context).setProgressText("Загружаем другую инфрмацию о ВУЗе...\n3/" + COUNT);

            /*
            Загрузка данных о столовых
            */
        s = null;
        while (s == null)
            s = url.get("common/campus/cafeteria/");
        String[] stol = s.split("<tr>");

        canteenObjects.clear();
        for (int i = 2; i < stol.length - 1; i++) {
            try {
                String[] stolSpl = stol[i].split("<p>");

                CanteenObject canteenObject = new CanteenObject(
                        i,
                        Jsoup.parse(stolSpl[2]).text(),
                        Jsoup.parse(stolSpl[3]).text(),
                        stol[i].split("</tr>")[0].split("<td>")[4]
                        );

                realm.beginTransaction();
                CanteenModel canteenModel = new CanteenModel();
                canteenModel.date = canteenObject.getDate();
                canteenModel.id = canteenObject.getId();
                canteenModel.name = canteenObject.getName();
                canteenModel.place = canteenObject.getPlace();
                realm.insertOrUpdate(canteenModel);
                realm.commitTransaction();

                canteenObjects.add(canteenObject);
            } catch (IndexOutOfBoundsException ex) {
                //ex.printStackTrace();
            }
        }

        ((LoadInformationActivity) context).setProgressText("Загружаем другую инфрмацию о ВУЗе...\n4/" + COUNT);

             /*
            Загрузка данных о библиотеках
            */ /*
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
                                Jsoup.parse(
                                        libs[j].split("<td>")[1].split("</td")[0]
                                                + "<!>" + libs[j].split("<td>")[2].split("</td")[0]
                                                .replaceAll("<sup>", " - ")
                                ).text()));
                        otdel.addChild(room);
                    } catch (IndexOutOfBoundsException ex) {}
                }
                libTree.addChild(otdel);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        } */

        ((LoadInformationActivity) context).setProgressText("Загружаем другую инфрмацию о ВУЗе...\n5/" + COUNT);

        realm.close();
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
                .replaceAll("</tr>", "")
                .replaceAll("<nobr>", "")
                .replaceAll("</nobr>", "");
    }

    /**
     * Получение списка столовых.
     * @return список столовых.
     */
    public ArrayList<CanteenObject> getCanteenList() {
        return canteenObjects;
    }
}
