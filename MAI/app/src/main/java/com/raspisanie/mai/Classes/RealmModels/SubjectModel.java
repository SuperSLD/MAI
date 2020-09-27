package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class SubjectModel extends RealmObject {
    @Required
    public String time;
    @Required
    public String type;
    @Required
    public String name;
    @Required
    public String lecturer;
    @Required
    public String place;
}
