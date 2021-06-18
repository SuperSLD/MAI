package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.extesions.toRealmList
import com.raspisanie.mai.models.local.DevLocal
import com.raspisanie.mai.models.realm.DevRealm
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.server.DevResponse
import com.raspisanie.mai.models.server.GroupResponse
import io.realm.RealmList

fun DevResponse.toRealm() = DevRealm(
    id = id,
    name = name,
    from = from,
    link = link
)

fun MutableList<DevResponse>.toRealm() = map { it.toRealm() }.toRealmList()

fun List<DevRealm>.toLocal() = map {it.toLocal()}.toMutableList()

fun DevRealm.toLocal() = DevLocal(
    id = id.orEmpty(),
    name = name.orEmpty(),
    from = from.orEmpty(),
    link = link.orEmpty()
)