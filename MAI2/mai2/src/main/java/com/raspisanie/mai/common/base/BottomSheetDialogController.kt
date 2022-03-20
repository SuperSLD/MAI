package com.raspisanie.mai.common.base

import com.raspisanie.mai.common.enums.BottomSheetDialogType
import online.jutter.supersld.common.datacontrol.PublishDataController

class BottomSheetDialogController {
    val state = PublishDataController<Pair<BottomSheetDialogType, Any?>>()

    fun show(type: BottomSheetDialogType, data: Any? = null) = state.emit(Pair(type, data))
}