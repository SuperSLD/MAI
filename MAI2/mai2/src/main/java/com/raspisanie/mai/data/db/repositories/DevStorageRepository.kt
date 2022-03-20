package com.raspisanie.mai.data.db.repositories

import com.raspisanie.mai.data.db.models.DevRealm
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.extesions.mappers.toLocal
import io.realm.Realm
import io.realm.RealmList

class DevStorageRepository(private val realm: Realm) {

    fun addAllDevs(devs: RealmList<DevRealm>) = with(realm) {
        executeTransaction {
            delete(DevRealm::class.java)
            copyToRealmOrUpdate(devs)
        }
    }

    fun getAllDevs(): MutableList<DevLocal> = with(realm) {
        return where(DevRealm::class.java).findAll().toLocal()
    }
}