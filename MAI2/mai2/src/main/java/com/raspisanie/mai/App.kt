package com.raspisanie.mai

import androidx.appcompat.app.AppCompatDelegate
import com.raspisanie.mai.di.appModule
import pro.midev.supersld.AppBase

class App : AppBase(appModule = appModule) {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}