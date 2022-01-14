package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.NewsLocal
import com.raspisanie.mai.models.realm.NewsRealm
import com.raspisanie.mai.models.server.NewsResponse

fun NewsResponse.toLocal() = NewsLocal(
    id = id,
    title = title,
    author = author,
    text = text,
    warning = warning,
    createdAt = createdAt,
    image = image,
)