package com.raspisanie.mai.extesions.realm

import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.models.local.DevLocal
import com.raspisanie.mai.models.local.NewsLocal
import com.raspisanie.mai.models.realm.DevRealm
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.realm.NewsRealm
import com.raspisanie.mai.models.realm.ScheduleRealm
import io.realm.Realm
import io.realm.RealmList
import java.security.acl.Group

fun Realm.addNews(devs: RealmList<NewsRealm>) {
    executeTransaction {
        delete(NewsRealm::class.java)
        copyToRealmOrUpdate(devs)
    }
}

fun Realm.getNews(): MutableList<NewsLocal> {
    return where(NewsRealm::class.java).findAll().map { it.toLocal() }.toMutableList()
}