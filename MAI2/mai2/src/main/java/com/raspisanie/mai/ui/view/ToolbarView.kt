package com.raspisanie.mai.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_toolbar.view.*
import pro.midev.supersld.extensions.addSystemTopPadding

class ToolbarView  : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        TVtoolbar.addSystemTopPadding()
    }

    /**
     * Инициализация тулбара.
     */
    fun init(
        title: Int,
        back: (() -> Unit)? = null,
        longTitle: Boolean = false
    ) {
        TVtvTitle.text = if (longTitle) toShort(context.getString(title)) else context.getString(title)

        if (back != null) {
            TVicClose.visibility = View.VISIBLE
            TVicClose.setOnClickListener { back() }
        }
    }

    private fun toShort(string: String): String {
        return if (string.length < 20) string else string.take(20) + "..."
    }

    /**
     * Инициализация тулбара.
     * Но текст заходит строкой.
     */
    fun init(
        title: String,
        back: (() -> Unit)? = null,
        longTitle: Boolean = false
    ) {
        TVtoolbar.addSystemTopPadding()
        TVtvTitle.text = if (longTitle) toShort(title) else title

        if (back != null) {
            TVicClose.visibility = View.VISIBLE
            TVicClose.setOnClickListener { back() }
        }
    }
}