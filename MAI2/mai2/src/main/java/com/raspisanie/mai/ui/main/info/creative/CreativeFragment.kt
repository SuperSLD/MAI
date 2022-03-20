package com.raspisanie.mai.ui.main.info.creative

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.CreativeLocal
import kotlinx.android.synthetic.main.fragment_creative.*
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class CreativeFragment : BaseFragment(R.layout.fragment_creative), CreativeView {

    @InjectPresenter
    lateinit var presenter: CreativePresenter
    private val adapter by lazy { CreativeAdapter(this::callPhone) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_creative,
            back = {onBackPressed()}
        )

        with(rvCreative){
            addSystemBottomPadding()
            adapter = this@CreativeFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    private fun callPhone(phone: String) {
        val uri = "tel:" + phone.trim()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        startActivity(intent)
    }

    override fun showList(mutableList: MutableList<CreativeLocal>) {
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