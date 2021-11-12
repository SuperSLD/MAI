package com.raspisanie.mai.ui.view.form

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager


@Suppress("NAME_SHADOWING")
class NonSwipeViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private var mEnabled = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (this.mEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return if (this.mEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        mEnabled = enabled
    }
}