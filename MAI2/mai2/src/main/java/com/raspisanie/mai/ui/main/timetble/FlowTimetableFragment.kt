package com.raspisanie.mai.ui.main.timetble

import android.os.Bundle
import com.raspisanie.mai.Screens
import online.jutter.supersld.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowTimetableFragment : FlowFragment(ROUTER) {

    companion object {
        const val ROUTER = "TIMETABLE_ROUTER"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.TimeTable)
                )
            )
        }
    }
}