package com.raspisanie.mai.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface MainContainerView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeBottomNavigationVisibility(isShow: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun changeBottomTab(screen: SupportAppScreen)

    @StateStrategyType(SkipStrategy::class)
    fun initBottomNavigation()
}
