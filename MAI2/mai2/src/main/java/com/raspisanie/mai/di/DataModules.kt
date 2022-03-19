package com.raspisanie.mai.di

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
}
