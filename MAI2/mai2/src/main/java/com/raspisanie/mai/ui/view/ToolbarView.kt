package com.raspisanie.mai.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getDrawable
import com.raspisanie.mai.extesions.getString
import kotlinx.android.synthetic.main.view_toolbar.view.*
import online.jutter.supersld.extensions.addSystemTopPadding

class ToolbarView  : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private var mHideDivider = false;

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        TVtoolbar.addSystemTopPadding()
    }

    /**
     * Скрывать разделительную полоску при инициализации.
     */
    fun hideDivider() {
        mHideDivider = true
    }

    /**
     * Инициализация тулбара.
     */
    fun init(
        title: Int,
        back: (() -> Unit)? = null,
        longTitle: Boolean = false,
        action: ToolbarAction? = null
    ) {
       init(getString(title), back, longTitle, action)
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
        longTitle: Boolean = false,
        action: ToolbarAction? = null
    ) {
        TVtoolbar.addSystemTopPadding()
        TVtvTitle.text = if (longTitle) toShort(title) else title

        if (mHideDivider) vDivider.visibility = GONE

        if (back != null) {
            TVicClose.visibility = View.VISIBLE
            TVicClose.setOnClickListener { back() }
        }

        if (action != null) {
            with(TVicFirst) {
                visibility = VISIBLE
                setImageDrawable(getDrawable(action.iconId))
                setOnClickListener {
                    action.action()
                }
            }
        }
    }

    /**
     * Кнопка в конце тулбара.
     */
    data class ToolbarAction(
        val iconId: Int,
        val action: ()->Unit
    )
}