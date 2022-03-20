package com.raspisanie.mai.ui.main.info.search_lector

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.TeacherLocal
import kotlinx.android.synthetic.main.fragment_search_lector.*
import kotlinx.android.synthetic.main.fragment_select_group.vToolbar
import kotlinx.android.synthetic.main.fragment_select_group.vgEmpty
import kotlinx.android.synthetic.main.fragment_select_group.vgMain
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class SearchLectorFragment : BaseFragment(R.layout.fragment_search_lector), SearchLectorView {

    @InjectPresenter
    lateinit var presenter: SearchLectorPresenter

    private val adapter by lazy { LectorsAdapter(presenter::select) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(vToolbar) {
            init(
                    title = R.string.info_search_lector_title,
                    back = { onBackPressed() }
            )
            initSearch(
                    {
                        showEmpty(true)
                    },
                    {
                        presenter.search(it)
                    },
                placeholder = getString(R.string.info_search_lector_placeholder)
            )
        }

        with(rvList) {
            adapter = this@SearchLectorFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        vgEmpty.addSystemBottomPadding()
        vgMain.addSystemBottomPadding()
        showEmpty(true)
    }

    private fun showEmpty(show: Boolean) {
        vgEmpty.visibility = if(show) View.VISIBLE else View.GONE
        rvList.visibility = if(show) View.GONE else View.VISIBLE
        cvError.setOnClickListener {
            presenter.search()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(groups: MutableList<TeacherLocal>) {
        showEmpty(groups.size == 0)
        adapter.addAll(groups)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        vgMain.visibility = View.GONE
    }

    override fun toggleLoading(show: Boolean) {
        vgMain.visibility = if (show) View.GONE else View.VISIBLE
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }
}