package com.raspisanie.mai.di

import com.raspisanie.mai.BuildConfig
import com.raspisanie.mai.common.base.BottomSheetDialogController
import com.raspisanie.mai.controllers.BottomVisibilityController
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.raspisanie.mai.server.Api
import com.raspisanie.mai.server.ApiService
import com.raspisanie.mai.common.CiceroneHolder
import com.raspisanie.mai.controllers.ChangeBottomTabController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
    }

    single {
        get<Retrofit.Builder>()
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }

    single {
        get<Retrofit>().create(Api::class.java)
    }

    single {
        Realm.init(androidContext())
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .name("mai_app.db")
            .build()
        Realm.getInstance(config)
    }

    single {
        ApiService(get())
    }

    single {
        BottomSheetDialogController()
    }

    single {
        BottomVisibilityController()
    }

    single {
        ChangeBottomTabController()
    }

    single {
        CiceroneHolder()
    }
}