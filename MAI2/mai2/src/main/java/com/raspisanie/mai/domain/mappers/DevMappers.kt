package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.common.extesions.toRealmList
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.data.db.models.DevRealm
import com.raspisanie.mai.data.net.models.DevResponse

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