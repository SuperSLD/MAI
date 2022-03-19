package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.data.db.models.GroupRealm
import io.realm.Realm

fun Realm.updateGroup(group: GroupRealm) {
    executeTransaction {
        copyToRealmOrUpdate(group)
    }
}

fun Realm.getCurrentGroup(): GroupRealm? {
    val list = where(GroupRealm::class.java).findAll()
    list.forEach {
        if (it.selected) {
            return it
        }
    }
    return null
}

fun Realm.getAllGroup(): MutableList<GroupRealm> {
    return where(GroupRealm::class.java).findAll()
}

/**
 * Удаление группы из реалма
 * и привязанного к ней расписания.
 */
fun GroupRealm.remove() {
    realm.executeTransaction { realm ->
        realm.removeSchedule(this)
        this.deleteFromRealm()
    }
}