package com.raspisanie.mai.Classes.RealmModels;

import com.raspisanie.mai.Classes.TimeTable.Subject;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DayModel extends RealmObject {
    @Required
    public String date;
    @Required
    public String name;
    public RealmList<SubjectModel> subjects;
}
