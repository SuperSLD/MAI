package com.raspisanie.mai.extesions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.raspisanie.mai.R
import com.raspisanie.mai.common.enums.ToastType

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Отображение красивого тоста.
 * @param toastType тип сообщения.
 * @param message само сообщение.
 */
@SuppressLint("InflateParams")
fun Context.showToast(toastType: ToastType, message: String = "") {
    val view = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false)
    val cvWrapper = view.findViewById<CardView>(R.id.cvWrapper)
    val tvMessage = view.findViewById<TextView>(R.id.tvMessage)

    tvMessage.text = when (toastType) {
        ToastType.ERROR -> {
            cvWrapper.setCardBackgroundColor(getColor(R.color.colorAlertRed))
            message
        }
        ToastType.SUCCESS -> {
            cvWrapper.setCardBackgroundColor(getColor(R.color.colorPrimarySecondary))
            message
        }
    }

    val toast = Toast(this)
    toast.setGravity(Gravity.BOTTOM, 0, 80.px)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = view
    toast.show()
}