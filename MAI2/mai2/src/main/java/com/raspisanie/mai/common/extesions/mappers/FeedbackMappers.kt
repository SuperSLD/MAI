package com.raspisanie.mai.extesions.mappers

import com.raspisanie.mai.data.net.models.FeedbackBody
import online.juter.supersld.view.input.form.JTForm

fun JTForm.toFeedbackBody(): FeedbackBody {
    return FeedbackBody(
        name = findString("name")!!,
        email = findString("email")!!,
        message = findString("message")!!,
    )
}