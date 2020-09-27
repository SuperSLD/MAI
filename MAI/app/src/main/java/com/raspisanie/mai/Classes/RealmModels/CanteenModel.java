package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CanteenModel extends RealmObject {
    @PrimaryKey
    public int id;
    @Required
    public String name;
    @Required
    public String date;
    @Required
    public String place;
}
