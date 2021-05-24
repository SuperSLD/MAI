package com.raspisanie.mai.ui.main.info.canteens

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.toList
import com.raspisanie.mai.models.realm.CanteenLocal
import com.yandex.metrica.impl.ob.V
import com.yandex.metrica.impl.ob.Vi
import kotlinx.android.synthetic.main.fragment_canteens.*
import kotlinx.android.synthetic.main.fragment_canteens.vToolbar
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class CanteensFragment : BaseFragment(R.layout.fragment_canteens), CanteensView {

    @InjectPresenter
    lateinit var presenter: CanteensPresenter

    private val adapter by lazy { CanteensAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
                title = R.string.canteens_title,
                back = {onBackPressed()}
        )

        with(rvCanteens) {
            addSystemBottomPadding()
            adapter = this@CanteensFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(mutableList: MutableList<CanteenLocal>) {
        adapter.addAll(mutableList)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        rvCanteens.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvCanteens.visibility = if (show) View.GONE else View.VISIBLE
    }
}