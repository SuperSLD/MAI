package com.raspisanie.mai.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.extesions.getIsDayTheme
import pro.midev.supersld.ActivityBase


class AppActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setWindowAnimations(R.style.WindowAnimationTransition)
    }

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