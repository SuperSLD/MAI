package com.raspisanie.mai.ui.main.settings
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.raspisanie.mai.extesions.getIsDayTheme
import com.raspisanie.mai.extesions.saveIsDayTheme
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings), MvpView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        container.addSystemTopPadding()
        scrollingContent.addSystemBottomPadding()

        swEnabled.isChecked = !context?.getIsDayTheme()!!

        swEnabled.setOnCheckedChangeListener { _, checked ->
            context?.saveIsDayTheme(!checked)
            if (!checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}