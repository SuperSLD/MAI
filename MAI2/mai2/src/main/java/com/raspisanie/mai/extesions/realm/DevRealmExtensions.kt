package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.models.local.DevLocal
import com.raspisanie.mai.models.realm.DevRealm
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.realm.ScheduleRealm
import io.realm.Realm
import io.realm.RealmList
import java.security.acl.Group

fun Realm.addAllDevs(devs: RealmList<DevRealm>) {
    executeTransaction {
        delete(DevRealm::class.java)
        copyToRealmOrUpdate(devs)
    }
}

fun Realm.getAllDevs(): MutableList<DevLocal> {
    return where(DevRealm::class.java).findAll().toLocal()
}