package com.raspisanie.mai.ui.main.exams

import android.os.Bundle
import android.view.View
import com.raspisanie.mai.Screens
import online.jutter.supersld.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowExamsFragment : FlowFragment(ROUTER) {

    companion object {
        const val ROUTER = "EXAMS_ROUTER"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Exams)
                )
            )
        }
    }
}