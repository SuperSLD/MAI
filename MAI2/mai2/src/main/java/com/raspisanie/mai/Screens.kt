package com.raspisanie.mai

import androidx.fragment.app.Fragment
import com.raspisanie.mai.domain.models.TeacherLocal
import com.raspisanie.mai.ui.global.FlowGlobalFragment
import com.raspisanie.mai.ui.main.FlowMainFragment
import com.raspisanie.mai.ui.main.MainContainerFragment
import com.raspisanie.mai.ui.main.exams.ExamsFragment
import com.raspisanie.mai.ui.main.exams.FlowExamsFragment
import com.raspisanie.mai.ui.main.info.FlowInfoFragment
import com.raspisanie.mai.ui.main.info.InfoFragment
import com.raspisanie.mai.ui.main.info.adv_list.AdvFragment
import com.raspisanie.mai.ui.main.info.adv_list.add_adv.AddAdvFragment
import com.raspisanie.mai.ui.main.info.adv_list.create_adv_success.CreateAdvSuccessFragment
import com.raspisanie.mai.ui.main.info.campus_map.CampusMapFragment
import com.raspisanie.mai.ui.main.info.canteens.CanteensFragment
import com.raspisanie.mai.ui.main.info.creative.CreativeFragment
import com.raspisanie.mai.ui.main.info.library.LibraryFragment
import com.raspisanie.mai.ui.main.info.news.NewsFragment
import com.raspisanie.mai.ui.main.info.search_lector.SearchLectorFragment
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.LectorScheduleFragment
import com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.calendar.CalendarFragment
import com.raspisanie.mai.ui.main.info.sport.SportFragment
import com.raspisanie.mai.ui.main.info.students.StudentsFragment
import com.raspisanie.mai.ui.main.settings.FlowSettingsFragment
import com.raspisanie.mai.ui.main.settings.SettingsFragment
import com.raspisanie.mai.ui.main.settings.add_group.AddGroupFragment
import com.raspisanie.mai.ui.main.settings.send_feedback.SendFeedbackFragment
import com.raspisanie.mai.ui.main.settings.send_feedback.success.SuccessFragment
import com.raspisanie.mai.ui.main.timetble.FlowTimetableFragment
import com.raspisanie.mai.ui.main.timetble.TimetableFragment
import com.raspisanie.mai.ui.main.timetble.blacklist.BlackListFragment
import com.raspisanie.mai.ui.main.timetble.new_group.NewGroupFragment
import com.raspisanie.mai.ui.main.timetble.select_number.SelectNumberFragment
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
        override fun getFragment(): Fragment = FlowSelectGroupFragment()
    }

    object Start : SupportAppScreen() {
        override fun getFragment(): Fragment = StartFragment()
    }

    object SelectGroup : SupportAppScreen() {
        override fun getFragment(): Fragment = SelectGroupFragment()
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
        override fun getFragment(): Fragment = FlowInfoFragment()
    }

    object Info: SupportAppScreen() {
        override fun getFragment(): Fragment = InfoFragment()
    }

    object CampusMap: SupportAppScreen() {
        override fun getFragment(): Fragment = CampusMapFragment()
    }

    object Canteens: SupportAppScreen() {
        override fun getFragment(): Fragment = CanteensFragment()
    }

    object Library: SupportAppScreen() {
        override fun getFragment(): Fragment = LibraryFragment()
    }

    object Sport: SupportAppScreen() {
        override fun getFragment(): Fragment = SportFragment()
    }

    object Students: SupportAppScreen() {
        override fun getFragment(): Fragment = StudentsFragment()
    }

    object Creative: SupportAppScreen() {
        override fun getFragment(): Fragment = CreativeFragment()
    }

    object News: SupportAppScreen() {
        override fun getFragment(): Fragment = NewsFragment()
    }

    object SearchLector: SupportAppScreen() {
        override fun getFragment(): Fragment = SearchLectorFragment()
    }

    data class LectorSchedule(val lector: TeacherLocal, val date: String = ""): SupportAppScreen() {
        override fun getFragment(): Fragment = LectorScheduleFragment.create(lector, date)
    }

    data class Calendar(val start: String, val end: String): SupportAppScreen() {
        override fun getFragment(): Fragment = CalendarFragment.create(start, end)
    }
    
    object AdvList: SupportAppScreen() {
        override fun getFragment(): Fragment = AdvFragment()
    }

    object AddAdv: SupportAppScreen() {
        override fun getFragment(): Fragment = AddAdvFragment()
    }

    object CreateAdvSuccess: SupportAppScreen() {
        override fun getFragment(): Fragment = CreateAdvSuccessFragment()
    }

    /** Экзаменационный роутер */

    object FlowExams : SupportAppScreen() {
        override fun getFragment(): Fragment = FlowExamsFragment()
    }

    object Exams: SupportAppScreen() {
        override fun getFragment(): Fragment = ExamsFragment()
    }

    /** Расписание - роутер */

    object FlowTimetable : SupportAppScreen() {
        override fun getFragment(): Fragment = FlowTimetableFragment()
    }

    object TimeTable: SupportAppScreen() {
        override fun getFragment(): Fragment = TimetableFragment()
    }

    data class SelectNumber(val number: Int?): SupportAppScreen() {
        override fun getFragment(): Fragment = SelectNumberFragment.create(number)
    }

    object NewGroup: SupportAppScreen() {
        override fun getFragment(): Fragment = NewGroupFragment()
    }

    object BlackList: SupportAppScreen() {
        override fun getFragment(): Fragment = BlackListFragment()
    }

    /** Настройки - роутер */

    object FlowSettings : SupportAppScreen() {
        override fun getFragment(): Fragment = FlowSettingsFragment()
    }

    object Settings: SupportAppScreen() {
        override fun getFragment(): Fragment = SettingsFragment()
    }

    object AddGroup: SupportAppScreen() {
        override fun getFragment(): Fragment = AddGroupFragment()
    }

    object Feedback: SupportAppScreen() {
        override fun getFragment(): Fragment = SendFeedbackFragment()
    }

    object Success: SupportAppScreen() {
        override fun getFragment(): Fragment = SuccessFragment()
    }
}