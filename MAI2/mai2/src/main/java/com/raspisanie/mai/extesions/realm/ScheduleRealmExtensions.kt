package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.realm.ScheduleRealm
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