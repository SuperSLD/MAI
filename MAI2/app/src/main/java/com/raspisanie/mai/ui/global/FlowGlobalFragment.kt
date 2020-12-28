package com.raspisanie.mai.ui.global

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.raspisanie.mai.Screens
import com.raspisanie.mai.Screens.APP_ROUTER
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.common.base.FlowFragment
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

    override fun showBottomSheet(type: BottomSheetDialogType, data: Any?) {
        /*val bottomSheet: MvpBottomSheetDialogFragment = when (type) {
            //BottomSheetDialogType.ADD_CLIENT -> ClientBSFragment()
        }

        bottomSheet.show(childFragmentManager, bottomSheet.tag)*/
        //TODO вернуть как появятся диалоги
    }
}