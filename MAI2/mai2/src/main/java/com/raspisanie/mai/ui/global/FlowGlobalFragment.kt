package com.raspisanie.mai.ui.global

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import com.raspisanie.mai.ui.main.settings.confirm_dialog.ConfirmBSFragment
import com.raspisanie.mai.ui.main.timetble.select_week_bs.SelectWeekBSFragment
import pro.midev.supersld.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowGlobalFragment : FlowFragment(ROUTER), GlobalView {

    companion object {
        const val ROUTER = "APP_ROUTER"
    }

    @InjectPresenter
    lateinit var presenter: GlobalPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Splash)
                )
            )
        }
    }

    /**
     * Вызов фрагмента.
     * @param type тип диалогового окна.
     * @param data данные необходимые для вызова фрагмента.
     */
    override fun showBottomSheet(type: BottomSheetDialogType, data: Any?) {
        val bottomSheet: MvpBottomSheetDialogFragment = when (type) {
            BottomSheetDialogType.SELECT_WEEK -> SelectWeekBSFragment()
            BottomSheetDialogType.CONFIRM -> ConfirmBSFragment.create((data as Pair<*, *>).first as String, data.second as String)
        }

        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}