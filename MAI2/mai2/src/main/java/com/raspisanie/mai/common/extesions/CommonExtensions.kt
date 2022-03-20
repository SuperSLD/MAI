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
 * Получение первых нескольких элементов списка.
 * @param count количество элементов.
 */
fun <T> MutableList<T>.firstItems(count: Int): MutableList<T> {
    val result = mutableListOf<T>()
    for (i in 0 until (if (count > this.size) this.size else count)) {
        result.add(this[i])
    }
    return result
}

/**
 * Duplicate any model for test.
 * @param count - size new list
 */
fun <T> T.toList(count: Int): MutableList<T> = mutableListOf<T>().apply {
    for (i in 0 until count) this.add(this@toList)
}

/**
 * Получение UUID строки.
 */
fun getUUID(): String {
    return UUID.randomUUID().toString()
}