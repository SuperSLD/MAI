package com.raspisanie.mai

import androidx.appcompat.app.AppCompatDelegate
import com.raspisanie.mai.di.appModule
import com.raspisanie.mai.extesions.getIsDayTheme
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import pro.midev.supersld.AppBase


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
    }

    private fun initYandex() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}