package com.raspisanie.mai.ui.main

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import kotlinx.android.synthetic.main.fragment_main_container.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainContainerFragment : BaseFragment(R.layout.fragment_main_container), MainContainerView {
    @InjectPresenter
    lateinit var presenter: MainContainerPresenter

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AHBottomNavigationAdapter(activity, R.menu.menu_bottom_navigation).apply {
            setupWithBottomNavigation(bottomNavigation)
        }

        with(bottomNavigation) {
            addSystemBottomPadding()

            accentColor =
                androidx.core.content.ContextCompat.getColor(context, R.color.colorPrimary)
            inactiveColor =
                androidx.core.content.ContextCompat.getColor(context, R.color.colorBottomNavigation)

            titleState =
                com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) selectTab(
                    when (position) {
                        0 -> infoTab
                        1 -> examsTab
                        2 -> timetableTab
                        3 -> settingsTab
                        else -> settingsTab
                    }
                )
                true
            }

            selectTab(
                when (currentTabFragment?.tag) {
                    settingsTab.screenKey -> settingsTab
                    else -> settingsTab
                }
            )

            bottomNavigation.currentItem = 2
        }
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                createTabFragment(tab)?.let {
                    replace(
                        R.id.vgFragmentContainer,
                        it,
                        tab.screenKey
                    )
                }
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun changeBottomTab(screen: SupportAppScreen) {
        bottomNavigation.currentItem = when (screen) {
            infoTab -> 0
            examsTab -> 1
            timetableTab -> 2
            settingsTab -> 3
            else -> 3
        }
        selectTab(screen)
    }

    override fun changeBottomNavigationVisibility(isShow: Boolean) {
        bottomNavigation.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed() ?: super.onBackPressed()
    }

    companion object {
        private val infoTab = Screens.FlowInfo
        private val examsTab = Screens.FlowExams
        private val timetableTab = Screens.FlowTimetable
        private val settingsTab = Screens.FlowSettings
    }

}