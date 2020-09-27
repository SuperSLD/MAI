package com.raspisanie.mai.Classes.RealmModels;

import com.raspisanie.mai.Classes.TimeTable.Day;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class WeekModel extends RealmObject {
    @PrimaryKey
    public int id;

    public int n;
    @Required
    public String date;

    public RealmList<DayModel> days;
}
