package com.raspisanie.mai.ui.main.info.sport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.SportLocal
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.fragment_sport.*
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class SportFragment : BaseFragment(R.layout.fragment_sport), SportView {

    @InjectPresenter
    lateinit var presenter: SportPresenter

    private val adapter by lazy { SportAdapter(this::onListClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_sport,
            back = {onBackPressed()}
        )

        with(rvSport){
            addSystemBottomPadding()
            adapter = this@SportFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    private fun onListClick(id: Int, data: Any?) {
        when(id) {
            SportAdapter.PHONE_ACTION -> callPhone(data as String)
        }
    }

    override fun showList(mutableList: MutableList<SportLocal>) {
        adapter.addData(mutableList)
    }

    private fun callPhone(phone: String) {
        val uri = "tel:" + phone.trim()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        startActivity(intent)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        rvSport.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvSport.visibility = if (show) View.GONE else View.VISIBLE
    }
}