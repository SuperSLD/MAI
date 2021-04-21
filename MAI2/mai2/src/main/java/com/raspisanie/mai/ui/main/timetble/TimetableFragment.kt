package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.SubjectLocal
import com.raspisanie.mai.models.local.WeekLocal
import com.raspisanie.mai.ui.view.ToolbarBigView
import kotlinx.android.synthetic.main.fragment_timetable.*
import pro.midev.supersld.common.base.BaseFragment

/**
 * Страшный фрагмент, со сложной логикой.
 * И дальше все будет только хуже.
 */
class TimetableFragment : BaseFragment(R.layout.fragment_timetable), TimetableView {

    @InjectPresenter
    lateinit var presenter: TimetablePresenter

    private val adapter by lazy { TimetableAdapter(
            presenter::onDaysListItemClick
    ) }
    private val headerAdapter by lazy { TimetableHeaderAdapter(
            presenter::onDayHeaderClick
    ) }

    private val scrollDaysListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

            if (firstVisibleItemPosition == 0) {
                headerAdapter.selectItem((adapter.getItemByPosition(0) as DayLocal).date)
                scrollToDay((adapter.getItemByPosition(0) as DayLocal).date)
            } else if (firstVisibleItemPosition > 0) {
                if (dy > 0) {
                    val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val lastVisibleItem = adapter.getItemByPosition(lastVisibleItemPosition)
                    if (lastVisibleItem is DayLocal) {
                        headerAdapter.selectItem(lastVisibleItem.date)
                        scrollToDay(lastVisibleItem.date)
                    }
                } else {
                    val firstVisibleItem = adapter.getItemByPosition(firstVisibleItemPosition)
                    if (firstVisibleItem is DayLocal) {
                        headerAdapter.selectItem(firstVisibleItem.date)
                        scrollToDay(firstVisibleItem.date)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        vToolbar.init(
                title = R.string.timetable_title_current
        )
        /*
        setTittleToolBar(include_toolbar, R.string.timetable_title_current, 0, R.drawable.ic_calendar, 0)
        include_toolbar.icFirst.setOnClickListener {
            presenter.selectWeekDialog()
        }
         */

        val week = initTimetable()

        adapter.addData(week)
        headerAdapter.addData(week)

        with(rvWeek) {
            adapter = this@TimetableFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(scrollDaysListener)
        }
        with(rvWeekHeader) {
            adapter = this@TimetableFragment.headerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    override fun selectDay(date: String) {
        headerAdapter.selectItem(date)
        rvWeek.removeOnScrollListener(scrollDaysListener)
        scrollToDay(date)
        scrollToDayInWeek(date)
    }

    private fun scrollToDayInWeek(date: String) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return 100
            }

            override fun onStop() {
                super.onStop()
                Handler().postDelayed({ rvWeek.addOnScrollListener(scrollDaysListener) }, 400)
            }
        }
        smoothScroller.targetPosition = adapter.getItemPosition(date)
        (rvWeek.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
    }

    private fun scrollToDay(date: String) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return 150
            }
        }
        smoothScroller.targetPosition = headerAdapter.getItemPosition(date)
        (rvWeekHeader.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
    }

    private fun initTimetable(): WeekLocal {
        //TODO Я не даун, это чисто для проверки верстки. Как буддет API уберу.
        return WeekLocal(
                number = 1,
                date = "04.01.2021 - 10.01.2021",
                days =  mutableListOf(
                        DayLocal(
                                date = "04.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayLocal(
                                date = "05.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "ВВИО(",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех с очень длинным названием, таким чтоб разметка поехала",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Введение в вариационные ичисления",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayLocal(
                                date = "06.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "Численные методы",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayLocal(
                                date = "07.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayLocal(
                                date = "08.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayLocal(
                                date = "09.01.2021",
                                subjects = mutableListOf(
                                        SubjectLocal(
                                                name = "Военная кафедра",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectLocal(
                                                name = "Военная кафедра",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        )
                                )
                        )
                )
        )
    }

    override fun onBackPressed() {
        presenter.back()
    }
}