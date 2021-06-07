package com.raspisanie.mai.ui.main.info.students

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.models.local.StudentOrganizationLocal
import kotlinx.android.synthetic.main.fragment_library.vToolbar
import kotlinx.android.synthetic.main.fragment_students.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class StudentsFragment : BaseFragment(R.layout.fragment_students), StudentsView {

    @InjectPresenter
    lateinit var presenter: StudentsPresenter

    private val adapter by lazy {StudentsAdapter(this::callPhone)}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vToolbar.init(
            title = R.string.info_button_students,
            back = {onBackPressed()}
        )

        with(rvStudents){
            addSystemBottomPadding()
            adapter = this@StudentsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
    override fun showList(mutableList: MutableList<StudentOrganizationLocal>) {
        adapter.addAll(mutableList)
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
        rvStudents.visibility = View.GONE
        cvError.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        vgError.visibility = View.GONE
        loading.visibility = if (show) View.VISIBLE else View.GONE
        rvStudents.visibility = if (show) View.GONE else View.VISIBLE
    }
}