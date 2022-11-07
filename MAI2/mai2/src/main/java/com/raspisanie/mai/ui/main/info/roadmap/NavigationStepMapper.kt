package com.raspisanie.mai.ui.main.info.roadmap

import online.jutter.roadmapview.data.models.nav.RMNavigationStep
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.DOWN
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.MOVE_IN_STRUCTURE
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.MOVE_IN_STRUCTURE_FROM_OUT
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.MOVE_ON_STREET
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.MOVE_OUT_FROM_STRUCTURE
import online.jutter.roadmapview.data.models.nav.RMNavigationStep.Companion.UP

fun RMNavigationStep.toText(nextFloor: Int? = null) = when(type) {
    UP -> "Дойдите до лестницы или лифта и поднимитесь на этаж $nextFloor"
    DOWN -> "Дойдите до лестницы или лифта и спуститесь на этаж $nextFloor"

    MOVE_IN_STRUCTURE -> "Пройдите по коридору"
    MOVE_ON_STREET -> "Пройдите по улице"

    MOVE_IN_STRUCTURE_FROM_OUT -> "Пройдите по улице до входа в $name"
    MOVE_OUT_FROM_STRUCTURE -> "Пройдите по коридору до выхода на улицу из $name"
    else -> "Без понятия что вам делать, ждите помощь бога"
}