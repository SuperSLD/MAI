package com.raspisanie.mai.ui.main.info.canteens

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.raspisanie.mai.ui.main.timetble.TimetableAdapter
import kotlinx.android.synthetic.main.fragment_canteens.*
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.fragment_timetable.include_toolbar
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class CanteensFragment : BaseFragment(R.layout.fragment_canteens), MvpView {

    @InjectPresenter
    lateinit var presenter: CanteensPresenter

    private val adapter by lazy { CanteensAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.canteens_title, R.drawable.ic_arrow_back, 0, 0)
        include_toolbar.btnClose.setOnClickListener {
            onBackPressed()
        }

        with(rvCanteens) {
            addSystemBottomPadding()
            adapter = this@CanteensFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}