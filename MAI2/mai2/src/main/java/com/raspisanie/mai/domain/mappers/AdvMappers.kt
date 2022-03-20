package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.domain.models.AdvLocal
import com.raspisanie.mai.data.net.models.AdvCreateBody
import com.raspisanie.mai.data.net.models.AdvResponse
import online.juter.supersld.view.input.form.JTForm

fun AdvResponse.toLocal() = AdvLocal(
    id = id,
    name = name,
    lastname = lastname,
    status = status,
    text = text,
    tg = tg,
    vk = vk,
    other = other,
    createdAt = createdAt,
    likeCount = likes,
    viewCount = views,
    isLike = like,
)

fun JTForm.toAdsCreateBody(): AdvCreateBody {
    return AdvCreateBody(
        name = findString("name")!!,
        lastname = findString("lastname")!!,
        text = findString("text")!!,
        vk = findString("vk")!!,
        tg = findString("tg")!!,
        other = findString("other")!!,
    )
}

fun MutableList<AdvResponse>.toLocal() = map { it.toLocal() }.toMutableList()