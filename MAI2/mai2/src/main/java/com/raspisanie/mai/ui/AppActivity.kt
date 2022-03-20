package com.raspisanie.mai.ui

import androidx.fragment.app.Fragment
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.usecases.state.GetThemeIsDayUseCase
import online.jutter.supersld.ActivityBase
import org.koin.android.ext.android.inject


class AppActivity : ActivityBase() {

    private val getThemeIsDayUseCase: GetThemeIsDayUseCase by inject()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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
        return getThemeIsDayUseCase()
    }
}