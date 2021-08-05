package com.raspisanie.mai.extesions.mappers

import com.google.gson.Gson
import com.raspisanie.mai.extesions.getUUID
import com.raspisanie.mai.extesions.toRealmList
import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.models.realm.*
import com.raspisanie.mai.models.server.*
import io.realm.RealmList
import java.util.*

fun RoomResponse.toRealm() = RoomRealm(id = id, name = name)
fun RoomRealm.toLocal() = RoomLocal(id = id ?: "---", name = name ?: "---")

fun TeacherResponse.toRealm() = TeacherRealm(id = id, name = name)
fun TeacherRealm.toLocal() = TeacherLocal(id = id ?: "---", name = name ?: "---")
fun TeacherResponse.toLocal() = TeacherLocal(id = id, name = name)


fun SubjectResponse.toRealm() = SubjectRealm(
        id = id,
        name = name,
        room = room.toRealm(),
        teacher = lector.toRealm(),
        type = type,
        link = link,
        timeStart = timeStart,
        timeEnd = timeEnd,
        number = number
)
fun SubjectRealm.toLocal(date: String) = SubjectLocal(
        id = id ?: "0",
        name = name ?: "---",
        room = room?.toLocal()!!,
        teacher = teacher?.toLocal()!!,
        type = type ?: "---",
        link = link ?: "---",
        timeStart = timeStart ?: "---",
        timeEnd = timeEnd ?: "---",
        number = number ?: "?",
        date = date
)

fun MutableList<SubjectResponse>.toRealmSubject() = this.map { it.toRealm() }.toRealmList()
fun RealmList<SubjectRealm>.toLocalSubjects(date: String) = this.map { it.toLocal(date) }.toMutableList()

fun DayResponse.toRealm() = DayRealm(
        id = id,
        date = date,
        subjects = subjects.toRealmSubject()
)
fun DayRealm.toLocal() = DayLocal(
        id = id ?: "0",
        date = date ?: "00.00.0000",
        subjects = subjects?.toLocalSubjects(date ?: "")!!
)

fun MutableList<DayResponse>.toRealm() = this.map { it.toRealm() }.toRealmList()
fun RealmList<DayRealm>.toLocal() = this.map { it.toLocal() }.toMutableList()

fun WeekResponse.toRealm() = WeekRealm(
        id = getUUID(),
        number = number,
        date = date,
        days = days.toRealm()
)
fun WeekRealm.toLocal() = WeekLocal(
        id = id ?: getUUID(),
        number = number ?: 0,
        date = date ?: "---",
        days = days?.toLocal()!!
)

fun MutableList<WeekResponse>.toRealm(groupId: String) = ScheduleRealm(
        groupId = groupId,
        weeks = this.map { it.toRealm() }.toRealmList(),
        lastUpdate = Calendar.getInstance().get(Calendar.DAY_OF_YEAR),
        size = Gson().toJson(this).toByteArray(Charsets.UTF_8).size/1024
)

fun RealmList<WeekRealm>.toLocalWeeks() = this.map { it.toLocal() }.toMutableList()

fun ScheduleRealm.toLocal() = ScheduleLocal(
        groupId = groupId!!,
        weeks = weeks?.toLocalWeeks()!!,
        size = size
)

fun MutableList<ScheduleRealm>.toLocal() = this.map { it.toLocal() }.toMutableList()
