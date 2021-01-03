package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.SubjectHuman
import com.raspisanie.mai.models.human.WeekHuman
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

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
                headerAdapter.selectItem((adapter.getItemByPosition(0) as DayHuman).date)
                scrollToDay((adapter.getItemByPosition(0) as DayHuman).date)
            } else if (firstVisibleItemPosition > 0) {
                if (dy > 0) {
                    val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val lastVisibleItem = adapter.getItemByPosition(lastVisibleItemPosition)
                    if (lastVisibleItem is DayHuman) {
                        headerAdapter.selectItem(lastVisibleItem.date)
                        scrollToDay(lastVisibleItem.date)
                    }
                } else {
                    val firstVisibleItem = adapter.getItemByPosition(firstVisibleItemPosition)
                    if (firstVisibleItem is DayHuman) {
                        headerAdapter.selectItem(firstVisibleItem.date)
                        scrollToDay(firstVisibleItem.date)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.timetable_title_current, 0, R.drawable.ic_calendar, 0)
        include_toolbar.btnFirst.setOnClickListener {
            presenter.selectWeekDialog()
        }

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

    fun initTimetable(): WeekHuman {
        //TODO Я не даун, это чисто для проверки верстки. Как буддет апи уберу.
        val week = WeekHuman(
                number = 1,
                date = "04.01.2021 - 10.01.2021",
                days =  mutableListOf(
                        DayHuman(
                                date = "04.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayHuman(
                                date = "05.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "ВВИО(",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех с очень длинным названием, таким чтоб разметка поехала",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Введение в вариационные ичисления",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayHuman(
                                date = "06.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "Численные методы",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayHuman(
                                date = "07.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayHuman(
                                date = "08.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "10:45 - 12:15",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
                                                name = "Термех",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "13:00 - 14:30",
                                                location = "LMS - Teams"
                                        )
                                )
                        ),
                        DayHuman(
                                date = "09.01.2021",
                                subjects = mutableListOf(
                                        SubjectHuman(
                                                name = "Военная кафедра",
                                                type = "пз",
                                                teacher = "Сухов Егор Аркадьевич",
                                                time = "9:00 - 10:30",
                                                location = "LMS - Teams"
                                        ),
                                        SubjectHuman(
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
        return week
    }

    override fun onBackPressed() {
        presenter.back()
    }
}