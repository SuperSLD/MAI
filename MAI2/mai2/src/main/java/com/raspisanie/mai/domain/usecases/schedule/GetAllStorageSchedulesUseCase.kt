package com.raspisanie.mai.domain.usecases.schedule

import com.raspisanie.mai.data.db.repositories.ScheduleStorageRepository
import com.raspisanie.mai.domain.mappers.toLocal

class GetAllStorageSchedulesUseCase(
    private val scheduleStorageRepository: ScheduleStorageRepository,
) {

    operator fun invoke() = scheduleStorageRepository.getAllSchedules().toLocal()
}