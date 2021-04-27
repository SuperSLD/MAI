package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.models.server.GroupResponse

fun GroupResponse.toLocal(): GroupRealm {
    return GroupRealm(
            id = id,
            name = name,
            fac = fac,
            level = level,
            course = course
    )
}

fun MutableList<GroupResponse>.toRealm(): MutableList<GroupRealm> {
    val result = mutableListOf<GroupRealm>()
    forEach {
        result.add(it.toLocal())
    }
    return result
}