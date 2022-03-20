package com.raspisanie.mai.domain.usecases.devs

import com.raspisanie.mai.data.db.models.DevRealm
import com.raspisanie.mai.data.db.repositories.DevStorageRepository
import io.realm.RealmList

class SaveInRealmDevsUseCase(
    private val devStorageRepository: DevStorageRepository,
) {

    operator fun invoke(devs: RealmList<DevRealm>) = devStorageRepository.addAllDevs(devs)
}