package com.raspisanie.mai.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.raspisanie.mai.Activity.LoadInformationActivity;
import com.raspisanie.mai.Classes.DataModels.CanteenObject;
import com.raspisanie.mai.Classes.DataModels.CreativeGroupObject;
import com.raspisanie.mai.Classes.DataModels.LibraryObject;
import com.raspisanie.mai.Classes.DataModels.SportGroupObject;
import com.raspisanie.mai.Classes.DataModels.StudentGroupObject;
import com.raspisanie.mai.Classes.RealmModels.CanteenModel;
import com.raspisanie.mai.Classes.RealmModels.CreativeGroupModel;
import com.raspisanie.mai.Classes.RealmModels.LibraryModel;
import com.raspisanie.mai.Classes.RealmModels.SportGroupModel;
import com.raspisanie.mai.Classes.RealmModels.SportSectionModel;
import com.raspisanie.mai.Classes.RealmModels.StudentGroupModel;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class OtherDataManager {
    private final String COUNT = "5";

    private ArrayList<CanteenObject> canteenObjects;
    private ArrayList<LibraryObject> libraryObjects;
    private ArrayList<SportGroupObject> sportGroupObjects;
    private ArrayList<StudentGroupObject> studentGroupObjects;
    private ArrayList<CreativeGroupObject> creativeGroupObjects;

    private static OtherDataManager otherDataManager;

    /**
     * Закрытый конструктор.
     */
    private OtherDataManager() {
        Realm realm = Realm.getDefaultInstance();

        this.canteenObjects = new ArrayList<>();
        this.libraryObjects = new ArrayList<>();
        this.sportGroupObjects = new ArrayList<>();
        this.studentGroupObjects = new ArrayList<>();
        this.creativeGroupObjects = new ArrayList<>();

        RealmResults<CanteenModel> canteenModels = realm.where(CanteenModel.class).findAll();
        for (CanteenModel canteenModel: canteenModels) {
            canteenObjects.add(new CanteenObject(
                    canteenModel.id,
                    canteenModel.name,
                    canteenModel.place,
                    canteenModel.date
            ));
        }

        RealmResults<LibraryModel> libraryModels = realm.where(LibraryModel.class).findAll();
        for (LibraryModel libraryModel : libraryModels) {
            LibraryObject libraryObject = new LibraryObject(libraryModel.name);
            for (String section : libraryModel.sections) {
                libraryObject.addItem(section);
            }
            libraryObjects.add(libraryObject);
        }

        RealmResults<SportGroupModel> sportGroupModels = realm.where(SportGroupModel.class).findAll();
        for (SportGroupModel sportGroupModel : sportGroupModels) {
            SportGroupObject sportGroupObject = new SportGroupObject(sportGroupModel.name);
            for (SportSectionModel sectionModel : sportGroupModel.sectionModels) {
                sportGroupObject.addSection(sectionModel.name,
                        sectionModel.administrator,
                        sectionModel.phoneNumber);
            }
            sportGroupObjects.add(sportGroupObject);
        }

        RealmResults<StudentGroupModel> studentGroupModels = realm.where(StudentGroupModel.class).findAll();
        for (StudentGroupModel studentGroupModel : studentGroupModels) {
            StudentGroupObject studentGroupObject = new StudentGroupObject(
                    studentGroupModel.name,
                    studentGroupModel.administrator,
                    studentGroupModel.phoneNumber,
                    studentGroupModel.information
            );
            studentGroupObjects.add(studentGroupObject);
        }

        RealmResults<CreativeGroupModel> creativeGroupModels = realm.where(CreativeGroupModel.class).findAll();
        for (CreativeGroupModel creativeGroupModel : creativeGroupModels) {
            CreativeGroupObject creativeGroupObject = new CreativeGroupObject(
                    creativeGroupModel.name,
                    creativeGroupModel.administrator,
                    creativeGroupModel.information
            );
            creativeGroupObjects.add(creativeGroupObject);
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
        realm.delete(LibraryModel.class);
        realm.delete(SportGroupModel.class);
        realm.delete(StudentGroupModel.class);
        realm.commitTransaction();

        url = new URLSendRequest("https://mai.ru/", 50000);
        String s = null;

            /*
            Загрузка данных о спортивных секциях
             */
        while (s == null)
            s = url.get("life/sport/sections.php");

        sportGroupObjects.clear();
        String[] korp = s.split("<th colspan=\"3\">");
        for (int i = 1; i < korp.length; i++) {
            SportGroupObject sportGroupObject = new SportGroupObject(
                    korp[i].split("</")[0].replaceAll("\t", "")
                            .replaceAll("&nbsp;", " ")
                            .replaceAll("\n", "")
                            .replaceAll("<p>", "")
            );

            String[] group = korp[i].split("<tr>");
            for (int j = 1; j < group.length; j++) {
                try {
                    sportGroupObject.addSection(
                            Jsoup.parse(group[j].split("</td>")[0].split("<td>")[1]).text(),
                            Jsoup.parse(deleteHTML(group[j].split("</td>")[1].split("<td>")[1])).text(),
                            Jsoup.parse(group[j].replaceAll("<!", "</")
                                    .replaceAll("<a", "</").split("</")[2].split("<td>")[1]).text()
                    );
                } catch (IndexOutOfBoundsException ex) {
                    //ex.printStackTrace();
                }
            }
            sportGroupObjects.add(sportGroupObject);
            realm.beginTransaction();
            SportGroupModel sportGroupModel = new SportGroupModel();
            sportGroupModel.id = i;
            sportGroupModel.name = sportGroupObject.getName();
            sportGroupModel.sectionModels = new RealmList<>();
            for (SportGroupObject.SportSection sportSection : sportGroupObject.getSportSections()) {
                SportSectionModel sportSectionModel = new SportSectionModel();
                sportSectionModel.name = sportSection.getName();
                sportSectionModel.administrator = sportSection.getAdministrator();
                sportSectionModel.phoneNumber = sportSection.getPhoneNumber();
                sportGroupModel.sectionModels.add(sportSectionModel);
            }
            realm.insertOrUpdate(sportGroupModel);
            realm.commitTransaction();
        }
        ((LoadInformationActivity) context).setProgressText("Загружаем другую инфрмацию о ВУЗе...\n1/" + COUNT);

            /*
            Загрузка данных о творческих колективах.
             */
        s = null;
        while (s == null)
            s = url.get("life/create/dkit/kollektivy-dkit.php");
        String[] group = s.split("<b>");

        this.creativeGroupObjects.clear();
        for (int i = 1; i < group.length; i++) {
            try {
                CreativeGroupObject creativeGroupObject = new CreativeGroupObject(
                        Jsoup.parse(group[i].split("</b>")[0]).text(),
                        Jsoup.parse(group[i].split("<p>")[1].split("</p>")[0]).text(),
                        Jsoup.parse(group[i].split("<p>")[2].split("</p>")[0]).text()
                );

                creativeGroupObjects.add(creativeGroupObject);

                realm.beginTransaction();
                CreativeGroupModel creativeGroupModel = new CreativeGroupModel();
                creativeGroupModel.id = i;
                creativeGroupModel.administrator = creativeGroupObject.getAdministrator();
                creativeGroupModel.information = creativeGroupObject.getInformation();
                creativeGroupModel.name = creativeGroupObject.getName();
                realm.insertOrUpdate(creativeGroupModel);
                realm.commitTransaction();
            } catch (IndexOutOfBoundsException ex) {
                //ex.printStackTrace();
            }
        }

        ((LoadInformationActivity) context).setProgressText("Загружаем другую информацию о ВУЗе...\n2/" + COUNT);

            /*
            Загрузка данных о студенческих организациях
            */
        s = null;
        while (s == null)
            s = url.get("life/join/index.php");

        String[] org = s.split("<th colspan=");

        this.studentGroupObjects.clear();
        for (int i = 1; i < org.length; i++) {
            try {
                StudentGroupObject studentGroupObject = new StudentGroupObject(
                        Jsoup.parse(org[i].replaceAll("<br>", "").split("</")[0].split(">")
                                [org[i].split("</")[0].split(">").length - 1]).text(),
                        Jsoup.parse(org[i].split("<td valign=\"top\">")[1].split("</td>")[0]).text(),
                        Jsoup.parse(org[i].split("<td colspan=\"2\" valign=\"top\">")[1].split("</td>")[0]).text(),
                        Jsoup.parse(org[i].split("<td>")[1].split("</td>")[0]).text()
                );

                realm.beginTransaction();
                StudentGroupModel studentGroupModel = new StudentGroupModel();
                studentGroupModel.administrator = studentGroupObject.getAdministrator();
                studentGroupModel.information = studentGroupObject.getInformation();
                studentGroupModel.name = studentGroupObject.getName();
                studentGroupModel.phoneNumber = studentGroupObject.getPhoneNumber();
                realm.insertOrUpdate(studentGroupModel);
                realm.commitTransaction();

                this.studentGroupObjects.add(studentGroupObject);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
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
                        Jsoup.parse(stol[i].split("</tr>")[0].split("<td>")[4]).text()
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
            */
        s = null;
        while (s == null)
            s = url.get("common/campus/library/");
        String[] lib = s.split("<th colspan=\"2\">");

        libraryObjects.clear();
        for (int i = 1; i < lib.length; i++) {
            try {
                LibraryObject libraryObject = new LibraryObject(
                        deleteHTML(lib[i].split("</th>")[0])
                );
                String[] libs = lib[i].split("<tr>");
                for (int j = 2; j < libs.length; j++) {
                    try {
                        libraryObject.addItem(deleteHTML(
                                Jsoup.parse(
                                        libs[j].split("<td>")[1].split("</td")[0]
                                                + "<!>" + libs[j].split("<td>")[2].split("</td")[0]
                                                .replaceAll("<sup>", " - ")
                                ).text()));
                    } catch (IndexOutOfBoundsException ex) {}
                }

                realm.beginTransaction();
                LibraryModel libraryModel = new LibraryModel();
                libraryModel.id = i;
                libraryModel.name = libraryObject.getName();
                libraryModel.sections = new RealmList<>();
                for (String section : libraryObject.getSections())
                    libraryModel.sections.add(section);
                realm.insertOrUpdate(libraryModel);
                realm.commitTransaction();
                libraryObjects.add(libraryObject);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }

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

    /**
     * Получение списка библиотек.
     * @return список библиотек.
     */
    public ArrayList<LibraryObject> getLibraryList() {
        return libraryObjects;
    }

    /**
     * Получение списка спортивных скций.
     * @return список спортивных секций.
     */
    public ArrayList<SportGroupObject> getSportGroupList() {
        return sportGroupObjects;
    }

    /**
     * Получение списка студенческих объединений.
     * @return список студенческих объединений.
     */
    public ArrayList<StudentGroupObject> getStudentGroupList() {
        return studentGroupObjects;
    }

    public ArrayList<CreativeGroupObject> getCreativeGroupList() {
        return creativeGroupObjects;
    }
}
