package com.raspisanie.mai.ui.main

import android.os.Bundle
import com.raspisanie.mai.Screens
import pro.midev.supersld.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowMainFragment : FlowFragment(ROUTER) {

    companion object {
        const val ROUTER = "CONTAINER_ROUTER"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.MainContainer)
                )
            )
        }
    }
}