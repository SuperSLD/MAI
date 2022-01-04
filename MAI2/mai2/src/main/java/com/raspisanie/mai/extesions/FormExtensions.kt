package com.raspisanie.mai.extesions

import android.content.Context
import com.raspisanie.mai.R
import online.juter.supersld.view.input.form.JTFormParams

fun Context.createFormParams() =
    JTFormParams().apply {
        colorAccent = R.color.colorPrimary
        colorBorder = R.color.colorBorder
        colorTextPrimary = R.color.colorTextPrimary
        colorTextSecondary = R.color.colorTextSecondary

        borderBackground = R.drawable.shape_grey_border

        buttonSolidStyle = R.style.ButtonSolid
        buttonEmptyStyle = R.style.ButtonEmpty
    }