package com.raspisanie.mai.ui.select_group

import android.os.Bundle
import android.view.View
import com.raspisanie.mai.Screens
import online.jutter.supersld.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowSelectGroupFragment : FlowFragment(ROUTER) {

    companion object {
        const val ROUTER = "SELECT_GROUP_ROUTER"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Start)
                )
            )
        }
    }
}