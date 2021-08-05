package com.raspisanie.mai.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_toolbar.view.*
import kotlinx.android.synthetic.main.view_toolbar.view.TVicClose
import kotlinx.android.synthetic.main.view_toolbar.view.TVtoolbar
import kotlinx.android.synthetic.main.view_toolbar.view.TVtvTitle
import kotlinx.android.synthetic.main.view_toolbar_search.view.*
import pro.midev.supersld.extensions.addSystemTopPadding

class ToolbarSearchView  : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    init {
        View.inflate(context, R.layout.view_toolbar_search, this)
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

    /**
     * Инициализация строки поиска.
     *
     * @param default вызывается когда строка отчищена.
     * @param search выхывается когда пользователь жмет поиск на клаве.
     */
    fun initSearch(
            default: () -> Unit,
            search: (String) -> Unit,
            placeholder: String? = null
    ) {
        with(TV_search) {
            setHintString(placeholder ?: context.getString(R.string.select_group_search))
            setClearIcon(R.drawable.ic_clear_line)
            onDefault { default() }
            onSearch { search(it) }
            init()
        }
    }
}