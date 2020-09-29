package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class StudentGroupModel extends RealmObject {
    @PrimaryKey
    public int id;
    @Required
    public String name;
    @Required
    public String administrator;
    @Required
    public String phoneNumber;
    @Required
    public String information;
}
