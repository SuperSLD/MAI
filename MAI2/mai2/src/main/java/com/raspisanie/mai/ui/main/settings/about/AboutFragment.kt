package com.raspisanie.mai.ui.main.settings.about

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.domain.models.DevLocal
import com.raspisanie.mai.common.extesions.openWebLink
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemTopPadding

class AboutFragment : BaseFragment(R.layout.fragment_about), AboutView {

    private val adapterDev by lazy { DevListAdapter(
        this::openLink
    ) }

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollingContent.addSystemTopPadding()

        btnBack.setOnClickListener {
            onBackPressed()
        }

        with(rvDevs) {
            adapter = this@AboutFragment.adapterDev
            layoutManager = LinearLayoutManager(context)
        }

        cvGit.setOnClickListener {
            openLink("https://github.com/SuperSLD/MAI")
        }
    }

    private fun openLink(link: String) {
        requireContext().openWebLink(link)
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showDevList(list: MutableList<DevLocal>) {
        adapterDev.addAll(list)
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        nested.visibility = if (show) View.GONE else View.VISIBLE
    }
}