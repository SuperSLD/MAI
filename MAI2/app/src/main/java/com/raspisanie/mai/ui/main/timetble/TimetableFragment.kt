package com.raspisanie.mai.ui.main.timetble
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.raspisanie.mai.models.human.DayHuman
import com.raspisanie.mai.models.human.SubjectHuman
import com.raspisanie.mai.models.human.WeekHuman
import kotlinx.android.synthetic.main.fragment_timetable.*

class TimetableFragment : BaseFragment(R.layout.fragment_timetable), MvpView {

    @InjectPresenter
    lateinit var presenter: TimetablePresenter

    private val adapter by lazy { TimetableAdapter() }
    private val headerAdapter by lazy { TimetableHeaderAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.timetable_title_current, 0, R.drawable.ic_calendar, 0)

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

        adapter.addData(week)
        headerAdapter.addData(week)

        with(rvWeek) {
            adapter = this@TimetableFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        with(rvWeekHeader) {
            adapter = this@TimetableFragment.headerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}