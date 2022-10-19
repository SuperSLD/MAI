package com.raspisanie.mai.ui.main.settings.feedback_response

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.data.net.models.FeedbackResponse
import kotlinx.android.synthetic.main.fragment_feedback_response.*
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.layout_loading.*
import online.jutter.supersld.common.base.BaseFragment
import online.jutter.supersld.extensions.addSystemBottomPadding

class FeedbackResponseFragment : BaseFragment(R.layout.fragment_feedback_response), FeedbackResponseView {

    @InjectPresenter
    lateinit var presenter: FeedbackResponsePresenter
    private val adapter by lazy { ResponseListAdapter(presenter::onNewQuestion) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vToolbar.init(
            title = R.string.settings_feedback_response_title,
            back = {onBackPressed()}
        )

        with(rvResponse){
            addSystemBottomPadding()
            adapter = this@FeedbackResponseFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(mutableList: List<FeedbackResponse>) {
        adapter.addData(mutableList)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        rvResponse.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvResponse.visibility = if (show) View.GONE else View.VISIBLE
    }
}