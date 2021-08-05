package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule.calendar

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.parseCalendarByFormat
import com.raspisanie.mai.ui.view.ToolbarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import online.juter.supersld.view.input.calendar.JTCalendarProperty
import online.juter.supersld.view.input.calendar.JTCalendarView
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CalendarView {

    @InjectPresenter
    lateinit var presenter: CalendarPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            hideDivider()
            init(
                    title = R.string.info_lector_schedule_calendar,
                    back = { onBackPressed() },
                    action = ToolbarView.ToolbarAction(
                        iconId = R.drawable.ic_done,
                        action = {
                            val date = vCalendar.getOne()
                            if (date == null) {
                                onBackPressed()
                            } else {
                                presenter.selectDate(date)
                            }
                        }
                    )
            )
        }

        vCalendar.init(
            JTCalendarProperty(
                startDate = arguments?.getString(ARG_START)!!.parseCalendarByFormat("dd.MM.yyyy"),
                endDate = arguments?.getString(ARG_END)!!.parseCalendarByFormat("dd.MM.yyyy"),
                selectMode = JTCalendarView.MODE_SELECT_ONE,

                headerTextColor = Color.parseColor("#979797"),
                headerColor = ContextCompat.getColor(requireContext(), R.color.colorBorder),

                textColor = ContextCompat.getColor(requireContext(), R.color.colorTextPrimary),

                selectedTextColor = ContextCompat.getColor(requireContext(), R.color.colorTextWhite),
                selectedColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                selectedBackDrawable = R.drawable.ic_calendar_selector,

                showLastMonth = false
            )
        )
        vCalendar.addSystemBottomPadding()
    }

    override fun onBackPressed() {
        presenter.back()
    }

    companion object {
        const val ARG_START = "arg_start"
        const val ARG_END = "arg_end"
        fun create(start: String, end: String) = CalendarFragment().apply {
            arguments = Bundle()
            arguments?.putString(ARG_START, start)
            arguments?.putString(ARG_END, end)
        }
    }
}