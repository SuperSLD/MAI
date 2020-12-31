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
import timber.log.Timber

class MainContainerFragment : BaseFragment(R.layout.fragment_main_container), MainContainerView {
    @InjectPresenter
    lateinit var presenter: MainContainerPresenter

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigation.addSystemBottomPadding()
    }

    override fun initBottomNavigation() {
        childFragmentManager.fragments.clear()
        AHBottomNavigationAdapter(activity, R.menu.menu_bottom_navigation).apply {
            setupWithBottomNavigation(bottomNavigation)
        }

        with(bottomNavigation) {

            accentColor =
                    androidx.core.content.ContextCompat.getColor(context, R.color.colorPrimarySecondary)
            inactiveColor =
                    androidx.core.content.ContextCompat.getColor(context, R.color.colorBottomNavigation)
            titleState =
                    com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) {
                    arguments?.putInt(ARG_POSITION, position)
                    Timber.d("position $position")
                    selectTab(
                            when (position) {
                                0 -> infoTab
                                1 -> examsTab
                                2 -> timetableTab
                                3 -> settingsTab
                                else -> settingsTab
                            }
                    )
                }
                true
            }

            selectTab(
                    when (arguments?.getInt(ARG_POSITION)!!) {
                        0 -> infoTab
                        1 -> examsTab
                        2 -> timetableTab
                        3 -> settingsTab
                        else -> settingsTab
                    }
            )

            bottomNavigation.currentItem = arguments?.getInt(ARG_POSITION)!!

            Timber.d("selected tab ${arguments?.getInt(ARG_POSITION)!!}")
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
        arguments?.putInt(ARG_POSITION, bottomNavigation.currentItem)
        Timber.d("position ${bottomNavigation.currentItem}")
        bottomNavigation.currentItem
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

        private const val ARG_POSITION = "arg_position"

        fun create(): MainContainerFragment {
            val fragment = MainContainerFragment()
            val arg = Bundle()
            arg.putInt(ARG_POSITION, 2)
            fragment.arguments = arg

            return fragment
        }
    }

}