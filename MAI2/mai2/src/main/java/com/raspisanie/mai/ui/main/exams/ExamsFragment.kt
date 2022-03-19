package com.raspisanie.mai.ui.main.exams
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.WeekLocal
import com.raspisanie.mai.ui.main.timetble.TimetableAdapter
import kotlinx.android.synthetic.main.fragment_exams.*
import pro.midev.supersld.common.base.BaseFragment

class ExamsFragment : BaseFragment(R.layout.fragment_exams), ExamsView {

    @InjectPresenter
    lateinit var presenter: ExamsPresenter

    private val adapter by lazy { TimetableAdapter(
            presenter::onDaysListItemClick
    ) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vToolbar.init(
                title = R.string.exams_title
        )

        with(rvWeek) {
            adapter = this@ExamsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        btnSelectGroup.setOnClickListener {
            presenter.goToSettings()
        }
    }

    override fun showExams(week: WeekLocal?) {
        if (week == null) {
            showEmpty(true)
        } else {
            showEmpty(false)
            adapter.addData(week, true)
        }
    }

    override fun showEmptyGroups() {
        vgEmpty.visibility = View.GONE
        vgContent.visibility = View.GONE
        vgSelectGroup.visibility = View.VISIBLE
    }

    private fun showEmpty(show: Boolean) {
        vgEmpty.visibility = if (show) View.VISIBLE else View.GONE
        vgContent.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun onBackPressed() {
        presenter.back()
    }
}