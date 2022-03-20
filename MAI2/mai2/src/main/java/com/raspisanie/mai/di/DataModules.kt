package com.raspisanie.mai.di

import com.raspisanie.mai.data.db.repositories.DevStorageRepository
import com.raspisanie.mai.data.db.repositories.GroupStorageRepository
import com.raspisanie.mai.data.db.repositories.ScheduleStorageRepository
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

fun Module.provideDataFlow() {
    single {
        Realm.init(androidContext())
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .name("mai_app.db")
            .build()
        Realm.getInstance(config)
    }

    single { ScheduleStorageRepository(get()) }
    single { GroupStorageRepository(get()) }
    single { DevStorageRepository(get()) }
}
