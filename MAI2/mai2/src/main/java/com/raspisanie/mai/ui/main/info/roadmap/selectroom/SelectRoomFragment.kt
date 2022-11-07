package com.raspisanie.mai.ui.main.info.roadmap.selectroom

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.fragment_selectroom.*
import kotlinx.android.synthetic.main.view_toolbar_search.view.*
import online.jutter.roadmapview.RMDataController
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding
import online.jutter.supersld.extensions.addSystemTopAndBottomPadding

class SelectRoomFragment : BaseFragment(R.layout.fragment_selectroom), MvpView {

    private val adapter by lazy { RoomsAdapter(presenter::onRoomClick) }

    @InjectPresenter
    lateinit var presenter: SelectRoomPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listContainer.addSystemTopAndBottomPadding()
        rvRooms.addSystemBottomPadding()
        btnBack.setOnClickListener { onBackPressed() }

        initRoomsRecycler()
        initSearch()
    }

    private fun initSearch() {
        with(vSearch) {
            setHintString(context.getString(R.string.roadmap_search_room))
            setClearIcon(R.drawable.ic_clear_line)
            onSearch { adapter.setSearchFilter(it) }
            init()
        }
    }

    private fun initRoomsRecycler() {
        with(rvRooms) {
            adapter = this@SelectRoomFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        adapter.addData(RMDataController.create("")!!.getMapFromCache()!!.getRooms())
    }

    override fun onBackPressed() {
        presenter.back()
    }
}