package com.raspisanie.mai.ui.global

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.Screens
import com.raspisanie.mai.Screens.APP_ROUTER
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.common.base.FlowFragment
import com.raspisanie.mai.common.base.MvpBottomSheetDialogFragment
import com.raspisanie.mai.ui.main.timetble.select_week_bs.SelectWeekBSFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowGlobalFragment : FlowFragment(APP_ROUTER), GlobalView {

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
        }

        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}