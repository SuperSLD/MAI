package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.models.realm.*
import io.realm.Realm
import java.security.acl.Group

fun Realm.updateSchedule(scheduleRealm: ScheduleRealm) {
    executeTransaction {
        copyToRealmOrUpdate(scheduleRealm)
    }
}

fun Realm.getCurrentSchedule(): ScheduleRealm? {
    return where(ScheduleRealm::class.java)
            .equalTo("groupId", getCurrentGroup()?.id)
            .findFirst()
}

fun Realm.getAllSchedules(): MutableList<ScheduleRealm> {
    return where(ScheduleRealm::class.java).findAll()
}

fun Realm.clearScheduleForCurrentGroup() {
    executeTransaction {
        getCurrentSchedule()?.deleteFromRealm()
    }
}

fun Realm.removeSchedule(group: GroupRealm) {
    where(ScheduleRealm::class.java)
            .equalTo("groupId", group.id)
            .findFirst()?.deleteFromRealm()
}

fun Realm.removeData() {
    executeTransaction {
        it.delete(GroupRealm::class.java)

        it.delete(TeacherRealm::class.java)
        it.delete(RoomRealm::class.java)
        it.delete(SubjectRealm::class.java)
        it.delete(DayRealm::class.java)
        it.delete(WeekRealm::class.java)
        it.delete(ScheduleRealm::class.java)
    }
}