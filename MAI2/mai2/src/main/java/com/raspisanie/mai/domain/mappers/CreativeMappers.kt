package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.data.net.models.CreativeResponse
import com.raspisanie.mai.domain.models.CreativeLocal

fun CreativeResponse.toLocal() = CreativeLocal(
    id = id,
    name = name,
    contactName = contactName,
    contact = contact,
    description = description
)

fun MutableList<CreativeResponse>.toLocal() = map { it.toLocal() }.toMutableList()