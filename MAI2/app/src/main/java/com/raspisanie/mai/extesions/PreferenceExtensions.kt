package com.raspisanie.mai.extesions

import android.content.Context
import com.google.gson.Gson
import pro.midev.iprofi.common.PreferenceHelper

const val IS_AUTH = "is_auth"
const val IS_DAY_THEME = "is_day_theme"


fun Context.saveAuthState(isAuth: Boolean) {
    PreferenceHelper.customPrefs(this, IS_AUTH).edit().putBoolean(IS_AUTH, isAuth).apply()
}

fun Context.getAuthState(): Boolean {
    return PreferenceHelper.customPrefs(this, IS_AUTH).getBoolean(IS_AUTH, false)
}

fun Context.saveIsDayTheme(day: Boolean) {
    PreferenceHelper.customPrefs(this, IS_DAY_THEME).edit().putBoolean(IS_DAY_THEME, day).apply()
}

fun Context.getIsDayTheme(): Boolean {
    return PreferenceHelper.customPrefs(this, IS_DAY_THEME).getBoolean(IS_DAY_THEME, true)
}