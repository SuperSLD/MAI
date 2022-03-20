package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.domain.models.NewsLocal
import com.raspisanie.mai.data.net.models.NewsResponse

fun NewsResponse.toLocal() = NewsLocal(
    id = id,
    title = title,
    author = author,
    text = text,
    warning = warning,
    createdAt = createdAt,
    image = image,
    imageDark = imageDark,
    likeCount = likes,
    viewCount = views,
    isLike = like,
)