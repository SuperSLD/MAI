package com.raspisanie.mai


import androidx.fragment.app.Fragment
import com.raspisanie.mai.ui.global.FlowGlobalFragment
import com.raspisanie.mai.ui.main.FlowMainFragment
import com.raspisanie.mai.ui.main.MainContainerFragment
import com.raspisanie.mai.ui.main.exams.ExamsFragment
import com.raspisanie.mai.ui.main.exams.FlowExamsFragment
import com.raspisanie.mai.ui.main.info.FlowInfoFragment
import com.raspisanie.mai.ui.main.info.InfoFragment
import com.raspisanie.mai.ui.main.settings.FlowSettingsFragment
import com.raspisanie.mai.ui.main.settings.SettingsFragment
import com.raspisanie.mai.ui.main.timetble.FlowTimetableFragment
import com.raspisanie.mai.ui.main.timetble.TimetableFragment
import com.raspisanie.mai.ui.select_group.FlowSelectGroupFragment
import com.raspisanie.mai.ui.select_group.start.StartFragment
import com.raspisanie.mai.ui.splash.SplashFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    const val APP_ROUTER = "APP_ROUTER"

    object FlowGlobal : SupportAppScreen() {
        override fun getFragment() = FlowGlobalFragment()
    }

    object Splash : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    const val SELECT_GROUP_ROUTER = "SELECT_GROUP_ROUTER"

    object FlowSelectGroup : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowSelectGroupFragment()
    }

    object Start : SupportAppScreen() {
        override fun getFragment(): Fragment? = StartFragment()
    }

    const val CONTAINER_ROUTER = "CONTAINER_ROUTER"

    object FlowMain : SupportAppScreen() {
        override fun getFragment() = FlowMainFragment()
    }

    object MainContainer : SupportAppScreen() {
        override fun getFragment() = MainContainerFragment.create()
    }

    const val INFO_ROUTER = "INFO_ROUTER"

    object FlowInfo : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowInfoFragment()
    }

    object Info: SupportAppScreen() {
        override fun getFragment(): Fragment? = InfoFragment()
    }

    const val EXAMS_ROUTER = "EXAMS_ROUTER"

    object FlowExams : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowExamsFragment()
    }

    object Exams: SupportAppScreen() {
        override fun getFragment(): Fragment? = ExamsFragment()
    }

    const val TIMETABLE_ROUTER = "TIMETABLE_ROUTER"

    object FlowTimetable : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowTimetableFragment()
    }

    object TimeTable: SupportAppScreen() {
        override fun getFragment(): Fragment? = TimetableFragment()
    }

    const val SETTINGS_ROUTER = "SETTINGS_ROUTER"

    object FlowSettings : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowSettingsFragment()
    }

    object Settings: SupportAppScreen() {
        override fun getFragment(): Fragment? = SettingsFragment()
    }
}