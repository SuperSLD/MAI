package com.raspisanie.mai.ui.global

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.enums.BottomSheetDialogType
import com.raspisanie.mai.common.enums.ToastType

interface GlobalView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showBottomSheet(type: BottomSheetDialogType, data: Any?)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(toastType: ToastType, message: String)
}