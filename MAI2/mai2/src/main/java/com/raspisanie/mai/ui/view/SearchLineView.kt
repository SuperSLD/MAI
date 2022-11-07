package com.raspisanie.mai.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo

/**
 * Линия поиска.
 */
@SuppressLint("ClickableViewAccessibility")
class SearchLineView : androidx.appcompat.widget.AppCompatEditText {

    private var clearIcon = -1

    private var canClear = false

    private var hintString = "Поиск"

    private var searchLambda = { _: String -> }
    private var defaultLambda = {}

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, def: Int) : super(
        context,
        attributeSet,
        def
    )

    /**
     * Инициализация поисковой строки.
     */
    fun init() {
        if (clearIcon < 0) throw Exception("clear icon not selected")

        val searchLine = this
        searchLine.showClearIcon(false)

        searchLine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (searchLine.text?.isNotEmpty()!!) {
                    searchLine.showClearIcon(true)
                    searchLambda(searchLine.text.toString())
                } else {
                    searchLine.showClearIcon(false)
                    defaultLambda()
                }
            }
            override fun afterTextChanged(s: Editable?) {}})
        searchLine.hint = hintString
        searchLine.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (searchLine.right - searchLine.compoundDrawables[2].bounds.width() - searchLine.paddingEnd)) {
                    if (canClear) {
                        searchLine.setText("")
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        searchLine.setOnEditorActionListener{ _, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                searchLambda(searchLine.text.toString())
            }
            return@setOnEditorActionListener false
        }

    }

    /**
     * Отображение иконки отчистки поля.
     */
    private fun showClearIcon(show: Boolean) {
        canClear = show
        setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0);
        compoundDrawables[2].alpha = if (show) 255 else 0
    }

    /**
     * Иконка отчистки поля.
     */
    fun setClearIcon(id: Int) {
        clearIcon = id;
    }

    /**
     * Вызывается при изменении текста.
     */
    fun onSearch(searchLambda: (String) -> Unit) {
        this.searchLambda = searchLambda
    }

    /**
     * Вызывается когда текст отчищен руками или через иконку.
     */
    fun onDefault(defaultLambda: () -> Unit) {
        this.defaultLambda = defaultLambda
    }

    /**
     * Установка hint.
     */
    fun setHintString(hint: String) {
        this.hintString = hint
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }
}