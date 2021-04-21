package com.raspisanie.mai

import androidx.fragment.app.Fragment
import com.raspisanie.mai.ui.global.FlowGlobalFragment
import com.raspisanie.mai.ui.main.FlowMainFragment
import com.raspisanie.mai.ui.main.MainContainerFragment
import com.raspisanie.mai.ui.main.exams.ExamsFragment
import com.raspisanie.mai.ui.main.exams.FlowExamsFragment
import com.raspisanie.mai.ui.main.info.FlowInfoFragment
import com.raspisanie.mai.ui.main.info.InfoFragment
import com.raspisanie.mai.ui.main.info.campus_map.CampusMapFragment
import com.raspisanie.mai.ui.main.info.canteens.CanteensFragment
import com.raspisanie.mai.ui.main.settings.FlowSettingsFragment
import com.raspisanie.mai.ui.main.settings.SettingsFragment
import com.raspisanie.mai.ui.main.timetble.FlowTimetableFragment
import com.raspisanie.mai.ui.main.timetble.TimetableFragment
import com.raspisanie.mai.ui.select_group.FlowSelectGroupFragment
import com.raspisanie.mai.ui.select_group.select_group.SelectGroupFragment
import com.raspisanie.mai.ui.select_group.start.StartFragment
import com.raspisanie.mai.ui.splash.SplashFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    /** Глобальный роутер */

    object FlowGlobal : SupportAppScreen() {
        override fun getFragment() = FlowGlobalFragment()
    }

    object Splash : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    /** Стартовый роутер */

    object FlowSelectGroup : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowSelectGroupFragment()
    }

    object Start : SupportAppScreen() {
        override fun getFragment(): Fragment? = StartFragment()
    }

    object SelectGroup : SupportAppScreen() {
        override fun getFragment(): Fragment? = SelectGroupFragment()
    }

    /** Контейнер роутер */

    object FlowMain : SupportAppScreen() {
        override fun getFragment() = FlowMainFragment()
    }

    object MainContainer : SupportAppScreen() {
        override fun getFragment() = MainContainerFragment.create()
    }

    /** Инфо роутер */

    object FlowInfo : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowInfoFragment()
    }

    object Info: SupportAppScreen() {
        override fun getFragment(): Fragment? = InfoFragment()
    }

    object CampusMap: SupportAppScreen() {
        override fun getFragment(): Fragment? = CampusMapFragment()
    }

    object Canteens: SupportAppScreen() {
        override fun getFragment(): Fragment? = CanteensFragment()
    }

    /** Экзаменационный роутер */

    object FlowExams : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowExamsFragment()
    }

    object Exams: SupportAppScreen() {
        override fun getFragment(): Fragment? = ExamsFragment()
    }

    /** Расписание - роутер */

    object FlowTimetable : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowTimetableFragment()
    }

    object TimeTable: SupportAppScreen() {
        override fun getFragment(): Fragment? = TimetableFragment()
    }

    /** Настройки - роутер */

    object FlowSettings : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowSettingsFragment()
    }

    object Settings: SupportAppScreen() {
        override fun getFragment(): Fragment? = SettingsFragment()
    }
}