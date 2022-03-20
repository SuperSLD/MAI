package com.raspisanie.mai.domain.usecases.groups

import com.raspisanie.mai.data.db.models.GroupRealm
import com.raspisanie.mai.data.db.repositories.GroupStorageRepository

class UpdateGroupUseCase(
    private val groupStorageRepository: GroupStorageRepository,
) {

    operator fun invoke(group: GroupRealm) = groupStorageRepository.updateGroup(group)
}