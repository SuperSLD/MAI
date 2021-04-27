package com.raspisanie.mai.ui.main.info.canteens

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.realm.CanteenLocal
import kotlinx.android.synthetic.main.fragment_canteens.*
import kotlinx.android.synthetic.main.fragment_canteens.vToolbar
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class CanteensFragment : BaseFragment(R.layout.fragment_canteens), MvpView {

    @InjectPresenter
    lateinit var presenter: CanteensPresenter

    private val adapter by lazy { CanteensAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
                title = R.string.canteens_title,
                back = {onBackPressed()}
        )

        adapter.addAll(createList())

        with(rvCanteens) {
            addSystemBottomPadding()
            adapter = this@CanteensFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun createList(): MutableList<CanteenLocal> {
        //TODO Убрать как будет апи и взять список с сайта
        return mutableListOf(
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
                        name = "Столовая 1",
                        location = "Дубосековская 1 к2",
                        time = "Пн-Пт 12:00-16:00 Сб 7:00-23:45"
                ),
                CanteenLocal(
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