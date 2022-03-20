package com.raspisanie.mai.domain.mappers

import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.data.net.models.GroupResponse
import com.raspisanie.mai.domain.models.GroupLocal

fun GroupResponse.toRealm(): GroupRealm {
    return GroupRealm(
            id = id,
            name = name,
            fac = fac,
            level = level,
            course = course
    )
}

fun GroupLocal.toRealm()=  GroupRealm(
    id = id,
    name = name,
    fac = fac,
    level = level,
    course = course
)

fun GroupResponse.toLocal() = GroupLocal(
        id = id,
        name = name,
        fac = fac,
        level = level,
        course = course
    )

fun MutableList<GroupResponse>.toRealm(): MutableList<GroupRealm> {
    val result = mutableListOf<GroupRealm>()
    forEach {
        result.add(it.toRealm())
    }
    return result
}