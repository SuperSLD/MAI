package com.raspisanie.mai.domain.usecases.main

import com.raspisanie.mai.data.db.ext.removeData
import io.realm.Realm

class RemoveRealmDataUseCase(
    private val realm: Realm,
) {

    operator fun invoke() = realm.removeData()
}