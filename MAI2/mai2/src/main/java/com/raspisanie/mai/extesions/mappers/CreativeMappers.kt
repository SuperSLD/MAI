package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.models.server.*

fun CreativeResponse.toLocal() = CreativeLocal(
    id = id,
    name = name,
    contactName = contactName,
    contact = contact,
    description = description
)

fun MutableList<CreativeResponse>.toLocal() = map { it.toLocal() }.toMutableList()