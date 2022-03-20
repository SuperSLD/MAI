package com.raspisanie.mai.domain.usecases.devs

import com.raspisanie.mai.data.db.repositories.DevStorageRepository

class GetAllDevsUseCase(
    private val devStorageRepository: DevStorageRepository,
) {

    operator fun invoke() = devStorageRepository.getAllDevs()
}