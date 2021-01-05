package com.raspisanie.mai.ui.main.info.canteens

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.models.human.CanteenHuman
import kotlinx.android.synthetic.main.fragment_canteens.*
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

        adapter.addAll(createList())

        with(rvCanteens) {
            addSystemBottomPadding()
            adapter = this@CanteensFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun createList(): MutableList<CanteenHuman> {
        //TODO Убрать как будет апи и взять список с сайта
        return mutableListOf(
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenHuman(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                )
        )
    }

    override fun onBackPressed() {
        presenter.back()
    }
}