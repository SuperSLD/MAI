package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.Subject
import com.raspisanie.mai.models.human.WeekHuman
import kotlinx.android.synthetic.main.fragment_timetable.*

class TimetableFragment : BaseFragment(R.layout.fragment_timetable), MvpView {

    @InjectPresenter
    lateinit var presenter: TimetablePresenter

    private val adapter by lazy { TimetableAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.timetable_title_current, 0, R.drawable.ic_calendar, 0)

        val week = WeekHuman(
                    number = 1,
                    date = "04.01.2021 - 10.01.2021",
                    days =  mutableListOf(
                            DayHuman(
                                    date = "04.01.2021",
                                    subjects = mutableListOf(
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "9:00 - 10:30",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "10:45 - 12:15",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "13:00 - 14:30",
                                                    location = "LMS - Teams"
                                            )
                                    )
                            ),
                            DayHuman(
                                    date = "04.01.2021",
                                    subjects = mutableListOf(
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "9:00 - 10:30",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "10:45 - 12:15",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "13:00 - 14:30",
                                                    location = "LMS - Teams"
                                            )
                                    )
                            ),
                            DayHuman(
                                    date = "04.01.2021",
                                    subjects = mutableListOf(
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "9:00 - 10:30",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "10:45 - 12:15",
                                                    location = "LMS - Teams"
                                            ),
                                            Subject(
                                                    name = "Термех",
                                                    type = "пз",
                                                    teacher = "Сухов Егор Аркадьевич",
                                                    time = "13:00 - 14:30",
                                                    location = "LMS - Teams"
                                            )
                                    )
                            )
                    )
        )

        adapter.addData(week)

        with(rvWeek) {
            adapter = this@TimetableFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}