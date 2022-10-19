package com.raspisanie.mai.domain.usecases.main

import com.raspisanie.mai.data.net.models.FeedbackResponse
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.models.LibraryLocal
import com.raspisanie.mai.extesions.mappers.toLocall
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadFeedbackResponseUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): List<FeedbackResponse> {
        val data = withIO { service.getFeedbackResponse() }
        return if (data.success) {
            data.data!!
        } else throw InvalidParameterException(data.message)
    }
}