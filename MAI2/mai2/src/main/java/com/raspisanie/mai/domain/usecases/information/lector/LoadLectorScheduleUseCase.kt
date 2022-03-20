package com.raspisanie.mai.domain.usecases.information.lector

import com.raspisanie.mai.data.net.models.WeekResponse
import com.raspisanie.mai.data.net.retrofit.ApiService
import online.jutter.supersld.extensions.withIO

class LoadLectorScheduleUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(id: String): MutableList<WeekResponse> {
        val data = withIO { service.getLectorSchedule(id) }
        return if (data.success) {
            data.data!!
        } else throw error(data.message?:"error")
    }
}