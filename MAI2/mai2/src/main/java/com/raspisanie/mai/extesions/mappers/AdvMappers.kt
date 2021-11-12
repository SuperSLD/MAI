package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.local.AdvLocal
import com.raspisanie.mai.models.realm.CanteenLocal
import com.raspisanie.mai.models.server.AdvCreateBody
import com.raspisanie.mai.models.server.AdvResponse
import com.raspisanie.mai.models.server.CanteenResponse
import com.raspisanie.mai.ui.view.form.Form
import com.raspisanie.mai.ui.view.form.lines.TextInputLine
import java.text.Normalizer
import kotlin.time.measureTimedValue

fun AdvResponse.toLocal() = AdvLocal(
    id = id,
    name = name,
    lastname = lastname,
    status = status,
    text = text,
    tg = tg,
    vk = vk,
    other = other,
    createdAt = createdAt
)

fun Form.toAdsCreateBody(): AdvCreateBody {
    val formLines = mutableListOf<TextInputLine>()
    pages.forEach { page ->
        page.lines.forEach { line ->
            if (line is TextInputLine) formLines.add(line)
        }
    }
    return AdvCreateBody(
        name = formLines.find { it.id == "name" }!!.value,
        lastname = formLines.find { it.id == "lastname" }!!.value,
        text = formLines.find { it.id == "text" }!!.value,
        vk = formLines.find { it.id == "vk" }!!.value,
        tg = formLines.find { it.id == "tg" }!!.value,
        other = formLines.find { it.id == "other" }!!.value
    )
}

fun MutableList<AdvResponse>.toLocal() = map { it.toLocal() }.toMutableList()