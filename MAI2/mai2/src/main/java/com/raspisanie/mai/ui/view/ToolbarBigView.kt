package com.raspisanie.mai.ui.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_toolbar_big.view.*
import pro.midev.supersld.extensions.addSystemTopPadding

class ToolbarBigView  : RelativeLayout {

    private var action: ToolbarAction? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    init {
        View.inflate(context, R.layout.view_toolbar_big, this)
        TVtoolbar.addSystemTopPadding()
    }

    /**
     * Инициализация тулбара.
     */
    fun init(
        title: Int,
        action: ToolbarAction? = null
    ) {
        TVtvTitle.text = context.getString(title)

        if (action != null) {
            this.action = action
            TV_action.visibility = View.VISIBLE
            TV_action.setOnClickListener {
                action.action()
            }
            TV_actionTitle.text = context.getString(action.stringId)
            TV_actionImage.setImageDrawable(ContextCompat.getDrawable(context, action.iconId))
        }
    }

    /**
     * Выключение боковой кнопки.
     */
    fun disableAction() {
        TV_actionTitle.setTextColor(ContextCompat.getColor(context, R.color.colorTextSecondary))
        TV_actionImage.setColorFilter(
                ContextCompat.getColor(context, R.color.colorTextSecondary),
                PorterDuff.Mode.SRC_IN
        )
        TV_action.setOnClickListener {  }
    }

    /**
     * Включение боковой кнопки.
     */
    fun enableAction() {
        TV_actionTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimarySecondary))
        TV_actionImage.setColorFilter(
                ContextCompat.getColor(context, R.color.colorPrimarySecondary),
                PorterDuff.Mode.SRC_IN
        )
        TV_action.setOnClickListener { action!!.action() }
    }

    /**
     * Замена заголовка у тулбара.
     * @param stringId
     */
    fun setTitle(stringId: Int) {
        TVtvTitle.text = context.getString(stringId)
    }

    /**
     * Замена заголовка у тулбара.
     * @param string
     */
    fun setTitle(string: String) {
        TVtvTitle.text = string
    }

    /**
     * Кнопка в конце тулбара.
     */
    data class ToolbarAction(
            val stringId: Int,
            val iconId: Int,
            val action: ()->Unit
    )
}