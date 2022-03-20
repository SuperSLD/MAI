package com.raspisanie.mai.ui.main.info.library

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.LibraryLocal
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class LibraryFragment : BaseFragment(R.layout.fragment_library), LibraryView {

    @InjectPresenter
    lateinit var presenter: LibraryPresenter

    private val adapter by lazy { LibraryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vToolbar.init(
            title = R.string.info_button_library,
            back = {onBackPressed()}
        )

        with(rvLibrary){
            addSystemBottomPadding()
            adapter = this@LibraryFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(mutableList: MutableList<LibraryLocal>) {
        adapter.addData(mutableList)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        rvLibrary.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvLibrary.visibility = if (show) View.GONE else View.VISIBLE
    }
}