package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class SportGroupModel extends RealmObject {
    @PrimaryKey
    public int id;
    @Required
    public String name;
    public RealmList<SportSectionModel> sectionModels;
}
