package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.SportLocal
import com.raspisanie.mai.models.local.SportSectionLocal
import com.raspisanie.mai.models.local.StudentOrganizationLocal
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.server.CanteenResponse
import com.raspisanie.mai.models.server.SportResponse
import com.raspisanie.mai.models.server.SportSectionResponse
import com.raspisanie.mai.models.server.StudentOrganizationResponse

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