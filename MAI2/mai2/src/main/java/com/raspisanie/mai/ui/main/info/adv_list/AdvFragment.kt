package com.raspisanie.mai.ui.main.info.adv_list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.openWebLink
import com.raspisanie.mai.domain.models.AdvLocal
import com.raspisanie.mai.ui.main.info.adv_list.AdvPagingParams.PAGE_SIZE
import kotlinx.android.synthetic.main.fragment_adv_list.*
import kotlinx.android.synthetic.main.fragment_canteens.vToolbar
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class AdvFragment : BaseFragment(R.layout.fragment_adv_list), AdvView {

    @InjectPresenter
    lateinit var presenter: AdvPresenter

    private val adapter by lazy { AdvListAdapter(
        this::tryLoadList,
        this::openLink,
        presenter::addAdv,
        presenter::like
    ) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            hideDivider()
            init(
                title = R.string.adv_title,
                back = { onBackPressed() }
            )
        }

        with(rvAdv) {
            addSystemBottomPadding()
            adapter = this@AdvFragment.adapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    val adp = this@AdvFragment.adapter
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

    private fun openLink(link: String) {
        requireContext().openWebLink(link)
    }

    override fun addList(list: MutableList<AdvLocal>) {
        adapter.toggleError(false)
        adapter.addData(list, list.size >= PAGE_SIZE)
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