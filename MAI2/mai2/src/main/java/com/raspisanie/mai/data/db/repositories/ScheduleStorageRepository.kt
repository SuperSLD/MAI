package com.raspisanie.mai.data.db.repositories

import com.raspisanie.mai.data.db.ext.getCurrentGroup
import com.raspisanie.mai.data.db.models.*
import io.realm.Realm

class ScheduleStorageRepository(private val realm: Realm) {

    fun updateSchedule(scheduleRealm: ScheduleRealm) = with(realm) {
        executeTransaction {
            copyToRealmOrUpdate(scheduleRealm)
        }
    }

    fun getCurrentSchedule(): ScheduleRealm? = with(realm) {
        return where(ScheduleRealm::class.java)
            .equalTo("groupId", getCurrentGroup()?.id)
            .findFirst()
    }

    fun getAllSchedules(): MutableList<ScheduleRealm> = with(realm) {
        return where(ScheduleRealm::class.java).findAll()
    }

    fun clearScheduleForCurrentGroup() = with(realm) {
        executeTransaction {
            getCurrentSchedule()?.deleteFromRealm()
        }
    }

    fun removeSchedule(group: GroupRealm) = with(realm) {
        where(ScheduleRealm::class.java)
            .equalTo("groupId", group.id)
            .findFirst()?.deleteFromRealm()
    }

    fun removeData() = with(realm) {
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
}