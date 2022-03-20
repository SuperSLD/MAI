package com.raspisanie.mai.domain.usecases.schedule

import com.raspisanie.mai.data.db.repositories.ScheduleStorageRepository
import com.raspisanie.mai.data.net.models.WeekResponse
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.mappers.toLocal
import com.raspisanie.mai.domain.models.GroupLocal
import online.jutter.supersld.extensions.withIO
import java.security.InvalidParameterException

class ClearScheduleForCurrentGroupUseCase(
    private val scheduleStorageRepository: ScheduleStorageRepository,
) {

    operator fun invoke() {
        scheduleStorageRepository.clearScheduleForCurrentGroup()
    }
}