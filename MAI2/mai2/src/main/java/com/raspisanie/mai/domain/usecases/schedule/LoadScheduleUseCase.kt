package com.raspisanie.mai.domain.usecases.schedule

import com.raspisanie.mai.data.net.models.WeekResponse
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.models.GroupLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class LoadScheduleUseCase(
    private val service: ApiService,
) {

    suspend operator fun invoke(groupId: String): MutableList<WeekResponse> {
        val data = withIO { service.getSchedule(groupId) }
        return if (data.success) {
            data.data!!
        } else throw error(data.message?:"error")
    }
}