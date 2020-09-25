package com.raspisanie.mai.Classes.DataModels;

import android.support.annotation.Nullable;

public class NewsObject {
    private int id;
    private String title;
    private String text;
    private String date;

    public NewsObject(int id,
                      String title,
                      String text,
                      String date) {
        this.id = id;
        this.text = text;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof NewsObject) {
            NewsObject news = ((NewsObject) obj);
            if (news.date.equals(this.date) &&
                news.title.equals(this.title) &&
                news.text.equals(this.text) &&
                news.id == this.id) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
