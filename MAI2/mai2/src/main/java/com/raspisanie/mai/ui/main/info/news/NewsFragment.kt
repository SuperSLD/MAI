package com.raspisanie.mai.ui.main.info.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.CreativeLocal
import com.raspisanie.mai.models.local.LibraryLocal
import com.raspisanie.mai.models.local.NewsLocal
import com.raspisanie.mai.ui.main.info.library.LibraryAdapter
import kotlinx.android.synthetic.main.fragment_campus_map.*
import kotlinx.android.synthetic.main.fragment_creative.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class NewsFragment : BaseFragment(R.layout.fragment_creative), NewsView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter
    private val adapter by lazy { NewsAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_news,
            back = {onBackPressed()}
        )

        with(rvCreative){
            addSystemBottomPadding()
            adapter = this@NewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(mutableList: MutableList<NewsLocal>) {
        adapter.addAll(mutableList)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        rvCreative.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvCreative.visibility = if (show) View.GONE else View.VISIBLE
    }
}