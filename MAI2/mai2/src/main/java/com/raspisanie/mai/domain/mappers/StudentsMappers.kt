package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.domain.models.StudentOrganizationLocal
import com.raspisanie.mai.data.net.models.StudentOrganizationResponse

fun StudentOrganizationResponse.toLocal() = StudentOrganizationLocal(
    name = name,
    contact = contact,
    address = address
)

fun MutableList<StudentOrganizationResponse>.toLocal() = map { it.toLocal() }.toMutableList()