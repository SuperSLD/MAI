package com.raspisanie.mai.ui.main.timetble.select_number

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.WeekLocal
import kotlinx.android.synthetic.main.fragment_select_number.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class SelectNumberFragment : BaseFragment(R.layout.fragment_select_number), SelectNumberView {

    @InjectPresenter
    lateinit var presenter: SelectNumberPresenter

    private val adapter by lazy { WeeksAdapter(
            presenter::select,
            arguments?.getInt(ARG_NUMBER)!!
    ) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            init(
                    title = R.string.timetable_select_week_number_title,
                    back = { onBackPressed() }
            )
        }

        with(rvWeeks) {
            adapter = this@SelectNumberFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        vgEmpty.addSystemBottomPadding()
        vgMain.addSystemBottomPadding()
        showEmpty(true)
    }

    private fun showEmpty(show: Boolean) {
        vgEmpty.visibility = if(show) View.VISIBLE else View.GONE
        rvWeeks.visibility = if(show) View.GONE else View.VISIBLE
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(weeks: MutableList<WeekLocal>?) {
        if (weeks != null) {
            showEmpty(weeks.size == 0)
            adapter.addAll(weeks)
        } else {
            showEmpty(true)
        }
    }

    companion object {
        const val ARG_NUMBER = "arg_number"

        fun create(
                number: Int?
        ): SelectNumberFragment {
            val fragment = SelectNumberFragment()

            val args = Bundle()
            args.putInt(ARG_NUMBER, number ?: -1)
            fragment.arguments = args

            return fragment
        }
    }
}