package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.server.CanteenResponse

fun CanteenResponse.toLocal() = CanteenLocal(
    name = name,
    time = workTime,
    location = address
)

fun MutableList<CanteenResponse>.toLocal() = map { it.toLocal() }.toMutableList()