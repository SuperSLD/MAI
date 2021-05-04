package com.raspisanie.mai.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.raspisanie.mai.R
import online.juter.supersld.view.JTProgressBar

/**
 * Переопределяем [JTProgressBar], чтобы изменить
 * дефолтные цвета которые выставляются при инициализации.
 *
 * Это избавит от необходимочти переопределять цвета при кажлом вызове.
 */
class JTProgressBarColored : JTProgressBar {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    override fun setDefaultColor(): MutableList<Int> {
        return mutableListOf(
            ContextCompat.getColor(context, R.color.colorPrimary),
            ContextCompat.getColor(context, R.color.colorGradientTop),
            ContextCompat.getColor(context, R.color.colorAccent),
            ContextCompat.getColor(context, R.color.colorGradientTopSecondary)
        )
    }
}