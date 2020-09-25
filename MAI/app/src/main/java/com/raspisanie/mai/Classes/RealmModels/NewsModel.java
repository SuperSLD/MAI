package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Модель хранения данных о новостной записи.
 * @author Соляной Леонид (solyanoy.leonid@gmail.com)
 */
public class NewsModel extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private String text;
    @Required
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
