package com.raspisanie.mai.ui

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.extesions.getIsDayTheme
import pro.midev.supersld.ActivityBase
import pro.midev.supersld.extensions.hideKeyboard
import timber.log.Timber


class AppActivity : ActivityBase() {
    override fun getStatusAndNavigationColor(): Pair<Int, Int> {
        return Pair(R.color.colorStatusBar, R.color.colorNavigationBar)
    }

    override fun getStartFragment(): Fragment {
        return Screens.FlowGlobal.fragment
    }

    override fun themeIsDay(): Boolean {
        // @everyone
        return baseContext.getIsDayTheme()
    }
}