package com.raspisanie.mai.domain.usecases.groups

import com.raspisanie.mai.data.db.repositories.GroupStorageRepository

class GetAllGroupsUseCase(
    private val groupStorageRepository: GroupStorageRepository,
) {

    operator fun invoke() = groupStorageRepository.getAllGroup()
}