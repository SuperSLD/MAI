package com.raspisanie.mai.ui.main.info.search_lector.lector_schedule

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.DayLocal
import com.raspisanie.mai.models.local.TeacherLocal
import kotlinx.android.synthetic.main.fragment_lector_schedule.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class LectorScheduleFragment : BaseFragment(R.layout.fragment_lector_schedule), LectorScheduleView {

    @InjectPresenter
    lateinit var presenter: LectorSchedulePresenter
    @ProvidePresenter
    fun provide() = LectorSchedulePresenter(arguments?.getParcelable(ARG_LECTOR)!!, arguments?.getString(ARG_DATE)!!)

    private val adapter by lazy {
        LectorScheduleAdapter(presenter::openCalendar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            init(
                    title = "",
                    back = { onBackPressed() }
            )
        }

        with(rvList) {
            adapter = this@LectorScheduleFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        vgMain.addSystemBottomPadding()
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showSchedule(day: DayLocal) {
        adapter.addData(
            arguments?.getParcelable(ARG_LECTOR)!!,
            day
        )
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        vgMain.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.load()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgMain.visibility = if (show) View.GONE else View.VISIBLE
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_LECTOR = "arg_lector"
        const val ARG_DATE = "arg_date"

        fun create(teacherLocal: TeacherLocal, date: String) = LectorScheduleFragment().apply {
            arguments = Bundle()
            arguments?.putParcelable(ARG_LECTOR, teacherLocal)
            arguments?.putString(ARG_DATE, date)
        }
    }
}