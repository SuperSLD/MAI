package com.raspisanie.mai.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.raspisanie.mai.Classes.DataModels.NewsObject;
import com.raspisanie.mai.Classes.RealmModels.NewsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Управление моделями новостных записей
 * и их сохранение в базе Realm.
 *
 * @author Соляной Леонид (solyanoy.leonid@gmail.com)
 */
public class NewsManager {

    private static NewsManager newsManager;

    private ArrayList<NewsObject> newsObjects = new ArrayList<>();
    private SharedPreferences settings;

    private NewsManager(SharedPreferences settings) {
        this.settings = settings;

        Realm realm = Realm.getDefaultInstance();
        Logger.getLogger("mailog").log(Level.INFO, "News try add from REALM .....");
        RealmResults<NewsModel> results = realm
                .where(NewsModel.class)
                .sort("date", Sort.DESCENDING)
                .findAll();

        for (int i = 0; i < results.size(); i++) {
            NewsModel newsModel = results.get(i);

            NewsObject news = new NewsObject(
                    newsModel.getId(),
                    newsModel.getTitle(),
                    newsModel.getText(),
                    newsModel.getDate()
            );
            Logger.getLogger("mailog").log(Level.INFO, "News add from REALM " + news.getTitle() +
                    "[" + news.getDate() + "]");
            newsObjects.add(news);
        }

        realm.close();
    }

    /**
     * Инициализация.
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    public static void init(SharedPreferences s) {
        if (newsManager == null) {
            newsManager = new NewsManager(s);
        }
    }

    /**
     * Получение текущего объекта.
     */
    public static NewsManager getInstance() {
        return newsManager;
    }

    /**
     * Добавление новости в приложение.
     * @param newsObject новость.
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void addNews(Realm realm, NewsObject newsObject) {
        newsObjects.add(0, newsObject);

        realm.beginTransaction();
        NewsModel newsModel = new NewsModel();
        newsModel.setDate(newsObject.getDate());
        newsModel.setText(newsObject.getText());
        newsModel.setTitle(newsObject.getTitle());
        newsModel.setId(newsObject.getId());
        //realm.copyToRealmOrUpdate(newsModel);
        realm.insertOrUpdate(newsModel);
        realm.commitTransaction();
    }

    /**
     * Получение списка новых новостей с сервера.
     *
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    public void loadNews() {
        URLSendRequest url = new URLSendRequest(Parametrs.SERVER_IP, 2000);
        new Thread(() -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                String s = null;
                while (s == null) {
                    Logger.getLogger("mailog").log(Level.INFO, "News try connect");
                    s = url.get("news?last_date=" + settings.getString("last_date", "2001.09.11 2017:56:33"));
                    Thread.sleep(10000);
                }
                Logger.getLogger("mailog").log(Level.INFO, "News JSON LOADED ->\n " + s);
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("news_list");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject newsObject = array.getJSONObject(i);
                    NewsObject news = new NewsObject(
                            Integer.parseInt(newsObject.getString("id")),
                            newsObject.getString("title"),
                            newsObject.getString("text"),
                            newsObject.getString("date")
                    );
                    Logger.getLogger("mailog").log(Level.INFO, "News add news " + news.getTitle() +
                            "[" + news.getDate() + "]");
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("last_date", news.getDate());
                    editor.apply();
                    addNews(realm, news);
                }
                realm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Получение списка объектов новостей.
     * @return новости.
     */
    public ArrayList<NewsObject> getNewsObjects() {
        return newsObjects;
    }
}