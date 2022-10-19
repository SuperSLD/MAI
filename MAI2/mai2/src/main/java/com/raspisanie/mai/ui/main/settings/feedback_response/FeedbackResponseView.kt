package com.raspisanie.mai.ui.main.settings.feedback_response

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.raspisanie.mai.common.base.BaseView
import com.raspisanie.mai.data.net.models.FeedbackResponse
import com.raspisanie.mai.domain.models.CreativeLocal

interface FeedbackResponseView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(mutableList: List<FeedbackResponse>)
}