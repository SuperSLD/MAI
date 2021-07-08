package com.raspisanie.mai.extesions

import android.content.Context
import com.google.gson.Gson
import com.raspisanie.mai.models.local.NotificationsLocal
import pro.midev.supersld.common.PreferenceHelper
import java.util.*

const val MAI_PREFS = "mai_prefs"

const val IS_AUTH = "is_auth"
const val IS_DAY_THEME = "is_day_theme"
const val SEMESTER = "semester"
const val NOTIFICATIONS = "notifications"

fun Context.saveAuthState(isAuth: Boolean) {
    PreferenceHelper.customPrefs(this, MAI_PREFS).edit().putBoolean(IS_AUTH, isAuth).apply()
}

fun Context.getAuthState(): Boolean {
    return PreferenceHelper.customPrefs(this, MAI_PREFS).getBoolean(IS_AUTH, false)
}

fun Context.saveIsDayTheme(day: Boolean) {
    PreferenceHelper.customPrefs(this, MAI_PREFS).edit().putBoolean(IS_DAY_THEME, day).apply()
}

fun Context.getIsDayTheme(): Boolean {
    return PreferenceHelper.customPrefs(this, MAI_PREFS).getBoolean(IS_DAY_THEME, true)
}

fun Context.saveSemester(sem: Int) {
    PreferenceHelper.customPrefs(this, MAI_PREFS).edit().putInt(SEMESTER, sem).apply()
}

fun Context.getSemester(): Int {
    return PreferenceHelper.customPrefs(this, MAI_PREFS).getInt(SEMESTER, 0)
}

fun Context.saveNotifications(notification: NotificationsLocal) {
    PreferenceHelper.customPrefs(this, MAI_PREFS).edit().putString(NOTIFICATIONS, Gson().toJson(notification)).apply()
}

fun Context.getNotifications(): NotificationsLocal {
    return Gson().fromJson(
        PreferenceHelper.customPrefs(this, MAI_PREFS).getString(NOTIFICATIONS, null),
        NotificationsLocal::class.java
    ) ?: NotificationsLocal(
        lastUpdate = Calendar.getInstance().toFormat("dd.MM.yyyy_HH:mm"),
        counts = hashMapOf()
    )
}