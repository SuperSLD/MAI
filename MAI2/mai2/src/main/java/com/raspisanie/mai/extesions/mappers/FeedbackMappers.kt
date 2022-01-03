package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.models.server.FeedbackBody
import com.raspisanie.mai.ui.view.form.Form
import com.raspisanie.mai.ui.view.form.lines.TextInputLine

fun Form.toFeedbackBody(): FeedbackBody {
    val formLines = mutableListOf<TextInputLine>()
    pages.forEach { page ->
        page.lines.forEach { line ->
            if (line is TextInputLine) formLines.add(line)
        }
    }
    return FeedbackBody(
        name = formLines.find { it.id == "name" }!!.value,
        email = formLines.find { it.id == "email" }!!.value,
        message = formLines.find { it.id == "message" }!!.value
    )
}