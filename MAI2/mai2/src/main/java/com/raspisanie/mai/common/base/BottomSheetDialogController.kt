package com.raspisanie.mai.common.base

import com.jakewharton.rxrelay3.PublishRelay
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import io.reactivex.rxjava3.core.Observable

class BottomSheetDialogController {
    private val stateRelay = PublishRelay.create<Pair<BottomSheetDialogType, Any?>>()

    val state: Observable<Pair<BottomSheetDialogType, Any?>> = stateRelay
    fun show(type: BottomSheetDialogType, data: Any? = null) = stateRelay.accept(Pair(type, data))
}