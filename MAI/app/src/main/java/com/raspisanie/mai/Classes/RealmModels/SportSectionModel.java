package com.raspisanie.mai.Classes.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class SportSectionModel extends RealmObject {
    @Required
    public String name;
    @Required
    public String administrator;
    @Required
    public String phoneNumber;
}
