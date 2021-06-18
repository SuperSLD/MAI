package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.*
import com.raspisanie.mai.models.server.*

fun LibrarySectionResponse.toLocal() = LibrarySectionLocal(
    name = name,
    location = location,
    id = id
)

fun MutableList<LibrarySectionResponse>.toLocal() = map { it.toLocal() }.toMutableList()

fun LibraryResponse.toLocal() = LibraryLocal(
    id = id,
    name = name,
    sections = sections.toLocal()
)

fun MutableList<LibraryResponse>.toLocall() = map { it.toLocal() }.toMutableList()