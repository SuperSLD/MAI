package com.raspisanie.mai.ui.main.settings.vk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.setVkAuth
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.models.realm.GroupRealm
import com.raspisanie.mai.ui.select_group.select_group.GroupsAdapter
import com.raspisanie.mai.ui.select_group.select_group.SelectGroupView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException
import kotlinx.android.synthetic.main.fragment_select_group.*
import kotlinx.android.synthetic.main.fragment_vk_more.*
import kotlinx.android.synthetic.main.layout_loading.*
import pro.midev.supersld.common.base.BaseFragment
import pro.midev.supersld.extensions.addSystemBottomPadding

class VkMoreFragment : BaseFragment(R.layout.fragment_vk_more), MvpView {

    @InjectPresenter
    lateinit var presenter: VkMorePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogIn.setOnClickListener {
            vkAuth()
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun vkAuth() {
        VK.login(requireActivity(), arrayListOf(VKScope.OFFLINE))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                requireContext().setVkAuth(token.accessToken)
                onBackPressed()
            }

            override fun onLoginFailed(authException: VKAuthException) {
                requireContext().showToast(
                    R.drawable.ic_close_toast,
                    requireContext().getString(R.string.settings_vk_more_auth_not_finished)
                )
                requireContext().setVkAuth(null)

            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        presenter.back()
    }
}