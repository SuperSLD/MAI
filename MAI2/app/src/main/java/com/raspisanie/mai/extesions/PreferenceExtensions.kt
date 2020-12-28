package com.raspisanie.mai.extesions

import android.content.Context
import com.google.gson.Gson
import pro.midev.iprofi.common.PreferenceHelper

const val IS_AUTH = "is_auth"

fun Context.saveAuthState(isAuth: Boolean) {
    PreferenceHelper.customPrefs(this, IS_AUTH).edit().putBoolean(IS_AUTH, isAuth).apply()
}

fun Context.getAuthState(): Boolean {
    return PreferenceHelper.customPrefs(this, IS_AUTH).getBoolean(IS_AUTH, false)
}