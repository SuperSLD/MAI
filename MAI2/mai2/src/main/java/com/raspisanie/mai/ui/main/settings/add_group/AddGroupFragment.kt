package com.raspisanie.mai.ui.main.settings.add_group

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.ui.select_group.select_group.GroupsAdapter
import com.raspisanie.mai.ui.select_group.select_group.SelectGroupView
import kotlinx.android.synthetic.main.fragment_select_group.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class AddGroupFragment : BaseFragment(R.layout.fragment_select_group), SelectGroupView {

    @InjectPresenter
    lateinit var presenter: AddGroupPresenter

    private val adapter by lazy { GroupsAdapter(presenter::select) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(vToolbar) {
            init(
                    title = R.string.settings_group_add_title,
                    back = { onBackPressed() }
            )
            initSearch(
                    {
                        showEmpty(true)
                    },
                    {
                        presenter.search(it)
                    }
            )
        }

        with(rvGroups) {
            adapter = this@AddGroupFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        vgEmpty.addSystemBottomPadding()
        vgMain.addSystemBottomPadding()
        showEmpty(true)
    }

    private fun showEmpty(show: Boolean) {
        vgEmpty.visibility = if(show) View.VISIBLE else View.GONE
        rvGroups.visibility = if(show) View.GONE else View.VISIBLE
        cvError.setOnClickListener {
            presenter.search()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(groups: MutableList<GroupRealm>) {
        showEmpty(groups.size == 0)
        adapter.addAll(groups)
    }

    override fun showErrorLoading() {
        vgError.visibility = View.VISIBLE
        loading.visibility = View.GONE
        vgMain.visibility = View.GONE
    }

    override fun toggleLoading(show: Boolean) {
        vgMain.visibility = if (show) View.GONE else View.VISIBLE
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }
}