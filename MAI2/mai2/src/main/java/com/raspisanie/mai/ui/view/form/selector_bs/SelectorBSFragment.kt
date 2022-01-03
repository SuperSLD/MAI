package com.raspisanie.mai.ui.view.form.selector_bs

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_selector.*
import com.raspisanie.mai.ui.view.form.lines.SelectorItem

class SelectorBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_selector), MvpView {

    @InjectPresenter
    lateinit var presenter: SelectorBSPresenter

    private val adapter by lazy { SelectorAdapter(this::selectItem) }
    private var onSelectL: ((SelectorItem)->Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.addAll(arguments?.getParcelableArrayList(ARG_DATA)!!)
        tvTitle.text = arguments?.getString(ARG_TITLE)

        with(rvItems) {
            adapter = this@SelectorBSFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun selectItem(item: SelectorItem) {
        onSelectL?.let { it(item) }
        dismiss()
    }

    fun onSelect(onSelectL: (SelectorItem)->Unit) {
        this.onSelectL = onSelectL
    }

    companion object {
        const val ARG_DATA = "arg_list"
        const val ARG_TITLE = "arg_title"

        fun create(
            title: String,
            list: MutableList<SelectorItem>
        ): SelectorBSFragment {
            val fragment = SelectorBSFragment()

            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putParcelableArrayList(ARG_DATA, ArrayList(list))
            fragment.arguments = args

            return fragment
        }
    }
}