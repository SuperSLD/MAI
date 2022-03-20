package com.raspisanie.mai.di

import com.raspisanie.mai.BuildConfig
import com.raspisanie.mai.data.net.retrofit.Api
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.data.net.retrofit.HeadersInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun Module.provideNetModules() {
    single {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(HeadersInterceptor(androidContext()))
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
        ApiService(get())
    }
}