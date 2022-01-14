package com.raspisanie.mai.ui.main.info.news

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.NewsLocal
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.fragment_news.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class NewsFragment : BaseFragment(R.layout.fragment_news), NewsView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter
    private val adapter by lazy { NewsListAdapter(this::tryLoadList, presenter::like) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            hideDivider()
            init(
                title = R.string.info_button_news,
                back = { onBackPressed() }
            )
        }

        with(rvNews){
            addSystemBottomPadding()
            adapter = this@NewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    val adp = this@NewsFragment.adapter
                    if (lm.findLastCompletelyVisibleItemPosition() == adp.listSize() && adp.hasMore()) {
                        presenter.loadList(adp.listSize())
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(mutableList: MutableList<NewsLocal>) {
        adapter.addData(mutableList, mutableList.size >= NewsPagingParams.PAGE_SIZE)
    }

    override fun updateLike(id: String) {
        adapter.updateLike(id)
    }

    override fun showErrorLoading() {
        adapter.toggleError(true)
    }

    private fun tryLoadList() {
        presenter.loadList(adapter.listSize())
    }

    override fun toggleLoading(show: Boolean) {}
}