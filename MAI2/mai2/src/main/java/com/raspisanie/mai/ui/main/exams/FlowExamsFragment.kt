package com.raspisanie.mai.ui.main.exams

import android.os.Bundle
import com.raspisanie.mai.Screens
import com.raspisanie.mai.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowExamsFragment : FlowFragment(Screens.EXAMS_ROUTER) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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