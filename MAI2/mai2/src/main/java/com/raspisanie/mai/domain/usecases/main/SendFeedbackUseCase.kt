package com.raspisanie.mai.domain.usecases.main

import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toFeedbackBody
import com.raspisanie.mai.domain.models.NotificationsLocal
import com.raspisanie.mai.domain.usecases.state.GetNotificationsUseCase
import com.raspisanie.mai.domain.usecases.state.SaveNotificationsUseCase
import online.juter.supersld.view.input.form.JTForm
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class SendFeedbackUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(form: JTForm) {
        val data = withIO { service.sendFeedback(form.toFeedbackBody()) }
        if (!data.success) throw InvalidParameterException(data.message)
    }
}