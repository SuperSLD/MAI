package com.raspisanie.mai.models.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ScheduleRealm(
        @PrimaryKey
        var groupId: String? = null,
        var weeks: RealmList<WeekRealm>? = RealmList(),
        var size: Int = 0,
        var lastUpdate: Int? = null
): RealmObject()

open class WeekRealm(
        @PrimaryKey
        var id: String? = null,
        var number: Int? = null,
        var date: String? = null,
        var days: RealmList<DayRealm>? = RealmList()
): RealmObject()

open class DayRealm(
        @PrimaryKey
        var id: String? = null,
        var date: String? = null,
        var subjects: RealmList<SubjectRealm>? = RealmList()
): RealmObject()

open class SubjectRealm(
        @PrimaryKey
        var id: String? = null,
        var name: String? = null,
        var room: RoomRealm? = null,
        var teacher: TeacherRealm? = null,
        var type: String? = null,
        var timeStart: String? = null,
        var timeEnd: String? = null,
        var link: String? = null,
        var number: String? = null
): RealmObject()

open class RoomRealm(
        @PrimaryKey
        var id: String? = null,
        var name: String? = null
): RealmObject()

open class TeacherRealm(
        @PrimaryKey
        var id: String? = null,
        var name: String? = null
): RealmObject()

