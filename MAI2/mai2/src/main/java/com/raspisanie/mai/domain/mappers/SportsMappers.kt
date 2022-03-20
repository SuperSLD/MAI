package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.domain.models.SportLocal
import com.raspisanie.mai.domain.models.SportSectionLocal
import com.raspisanie.mai.data.net.models.SportResponse
import com.raspisanie.mai.data.net.models.SportSectionResponse

fun SportSectionResponse.toLocal() = SportSectionLocal(
    name = name,
    contact = contact,
    contactName = contactName,
    id = id
)

fun MutableList<SportSectionResponse>.toLocal() = map { it.toLocal() }.toMutableList()

fun SportResponse.toLocal() = SportLocal(
    id = id,
    address = address,
    name = name,
    sections = sections.toLocal()
)

fun MutableList<SportResponse>.toLocall() = map { it.toLocal() }.toMutableList()