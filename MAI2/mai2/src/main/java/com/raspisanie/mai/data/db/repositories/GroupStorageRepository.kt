package com.raspisanie.mai.data.db.repositories

import com.raspisanie.mai.data.db.ext.removeSchedule
import com.raspisanie.mai.data.db.models.GroupRealm
import io.realm.Realm

class GroupStorageRepository(private val realm: Realm) {

    fun updateGroup(group: GroupRealm) = with(realm) {
        executeTransaction {
            copyToRealmOrUpdate(group)
        }
    }

    fun getCurrentGroup(): GroupRealm? = with(realm) {
        val list = where(GroupRealm::class.java).findAll()
        list.forEach {
            if (it.selected) {
                return it
            }
        }
        return null
    }

    fun getAllGroup(): MutableList<GroupRealm> = with(realm) {
        return where(GroupRealm::class.java).findAll()
    }
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