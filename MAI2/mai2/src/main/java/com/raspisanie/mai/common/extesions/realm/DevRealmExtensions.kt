package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.data.db.models.DevRealm
import io.realm.Realm
import io.realm.RealmList

fun Realm.addAllDevs(devs: RealmList<DevRealm>) {
    executeTransaction {
        delete(DevRealm::class.java)
        copyToRealmOrUpdate(devs)
    }
}

fun Realm.getAllDevs(): MutableList<DevLocal> {
    return where(DevRealm::class.java).findAll().toLocal()
}