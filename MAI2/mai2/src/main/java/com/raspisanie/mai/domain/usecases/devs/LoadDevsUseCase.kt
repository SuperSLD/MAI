package com.raspisanie.mai.domain.usecases.devs

import com.raspisanie.mai.data.net.models.DevResponse
import com.raspisanie.mai.data.net.retrofit.ApiService
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadDevsUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(): MutableList<DevResponse>? {
        val data = withIO { service.getDevList() }
        return if (data.success) {
            data.data
        } else throw InvalidParameterException(data.message)
    }
}