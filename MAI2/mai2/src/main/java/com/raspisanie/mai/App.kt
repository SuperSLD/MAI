package com.raspisanie.mai

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.raspisanie.mai.common.extesions.getIsDayTheme
import com.raspisanie.mai.di.appModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import online.jutter.roadmapview.RMMapID
import online.jutter.roadmapview.RMMapView
import online.jutter.supersld.AppBase

class App : AppBase(appModule = appModule()) {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
                if (baseContext.getIsDayTheme()) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
        )
        initYandex()
        RMMapView.setApiKey("9b8d2e6b-bdd9-4379-9e01-5c2b74cf728f")
    }

    private fun initYandex() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}