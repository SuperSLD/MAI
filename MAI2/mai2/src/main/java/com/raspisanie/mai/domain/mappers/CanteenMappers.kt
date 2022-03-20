package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.data.db.models.CanteenLocal
import com.raspisanie.mai.data.net.models.CanteenResponse

fun CanteenResponse.toLocal() = CanteenLocal(
    name = name,
    time = workTime,
    location = address
)

fun MutableList<CanteenResponse>.toLocal() = map { it.toLocal() }.toMutableList()