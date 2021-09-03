package com.raspisanie.mai.extesions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.raspisanie.mai.R

/**
 * ????????????
 * миленько, но зачем оно тут хз
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Отображение красивого тоста с текстом
 * и иконкой.
 * @param iconId иконка.
 * @param message само сообщение.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
@SuppressLint("InflateParams")
fun Context.showToast(iconId: Int, message: String = "", isLong: Boolean = false) {
    val view = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false)
    val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
    val ivIcon = view.findViewById<ImageView>(R.id.ivIcon)

    tvMessage.text = message
    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, iconId))

    val toast = Toast(this)
    toast.setGravity(Gravity.BOTTOM, 0, 80.px)
    toast.duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    toast.view = view
    toast.show()
}