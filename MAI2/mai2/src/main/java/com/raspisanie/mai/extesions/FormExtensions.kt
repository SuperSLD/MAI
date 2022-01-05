package com.raspisanie.mai.extesions

import android.content.Context
import android.graphics.Color
import com.raspisanie.mai.R
import online.juter.supersld.view.input.form.JTFormParams

fun Context.createFormParams() =
    if (getIsDayTheme())
        JTFormParams().apply {
            colorAccent = Color.parseColor("#0596FF")
            colorBorder = Color.parseColor("#E1E1E1")
            colorTextPrimary = Color.parseColor("#333333")
            colorTextSecondary = Color.parseColor("#909DA9")

            borderBackground = R.drawable.shape_grey_border

            buttonSolidStyle = R.style.ButtonSolid
            buttonEmptyStyle = R.style.ButtonEmpty
        }
    else
        JTFormParams().apply {
            colorAccent = Color.parseColor("#0596FF")
            colorBorder = Color.parseColor("#292929")
            colorTextPrimary = Color.parseColor("#FFFFFF")
            colorTextSecondary = Color.parseColor("#909DA9")

            borderBackground = R.drawable.shape_grey_border

            buttonSolidStyle = R.style.ButtonSolid
            buttonEmptyStyle = R.style.ButtonEmpty
        }