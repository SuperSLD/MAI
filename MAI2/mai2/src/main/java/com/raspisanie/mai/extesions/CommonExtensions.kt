package com.raspisanie.mai.extesions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import io.realm.RealmList
import io.realm.RealmObject
import java.util.*

fun Vibrator.shortVibration() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrate(100)
    }
}

fun Context.openWebLink(link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    startActivity(intent)
}

/**
 * Переход от списка к реалм списку.
 */
fun <T : RealmObject> List<T>.toRealmList(): RealmList<T> {
    val result = RealmList<T>()
    forEach { result.add(it) }
    return result
}

/**
 * Получение UUID строки.
 */
fun getUUID(): String {
    return UUID.randomUUID().toString()
}