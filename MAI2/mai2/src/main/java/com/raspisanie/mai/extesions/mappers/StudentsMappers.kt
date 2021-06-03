package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.StudentOrganizationLocal
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.server.CanteenResponse
import com.raspisanie.mai.models.server.StudentOrganizationResponse

fun StudentOrganizationResponse.toLocal() = StudentOrganizationLocal(
    name = name,
    contact = contact,
    address = address
)

fun MutableList<StudentOrganizationResponse>.toLocal() = map { it.toLocal() }.toMutableList()