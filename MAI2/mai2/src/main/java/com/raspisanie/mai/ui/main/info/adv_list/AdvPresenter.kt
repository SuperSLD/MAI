package com.raspisanie.mai.ui.main.info.adv_list

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.raspisanie.mai.R
import com.raspisanie.mai.Screens
import com.raspisanie.mai.domain.controllers.BottomVisibilityController
import com.raspisanie.mai.domain.usecases.information.adv.LoadAdvUseCase
import com.raspisanie.mai.domain.usecases.information.adv.SetLikeAdvUseCase
import com.raspisanie.mai.extesions.showToast
import com.raspisanie.mai.ui.ext.createHandler
import com.raspisanie.mai.ui.main.info.adv_list.AdvPagingParams.PAGE_SIZE
import com.yandex.metrica.YandexMetrica
import online.jutter.supersld.common.base.BasePresenter
import online.jutter.supersld.extensions.launchUI
import online.jutter.supersld.extensions.withIO
import org.koin.core.inject

@InjectViewState
class AdvPresenter : BasePresenter<AdvView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val context: Context by inject()
    private val loadAdvUseCase: LoadAdvUseCase by inject()
    private val setLikeAdvUseCase: SetLikeAdvUseCase by inject()

    override fun attachView(view: AdvView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        YandexMetrica.reportEvent("OpenAdvList")
        loadList(0)
    }

    fun loadList(skip: Int) {
        val handler = createHandler {
            viewState.showErrorLoading()
            context.showToast(R.drawable.ic_close_toast, it.message.toString())
        }
        launchUI(handler) {
            viewState.toggleLoading(true)
            val list = withIO { loadAdvUseCase(PAGE_SIZE, skip) }
            viewState.toggleLoading(false)
            viewState.addList(list)
        }
    }

    fun like(id: String) {
        val handler = createHandler {
            context.showToast(R.drawable.ic_close_toast, it.message.toString())
        }
        launchUI(handler) {
            val isLiked = withIO { setLikeAdvUseCase(id) }
            context.showToast(
                R.drawable.ic_like_toast,
                context.getString(if (isLiked) R.string.like_success else R.string.like_error)
            )
            if (isLiked) viewState.updateLike(id)
        }
    }

    fun addAdv() {
        router?.navigateTo(Screens.AddAdv)
    }

    fun back() = router?.exit()
}

object AdvPagingParams {
    const val PAGE_SIZE = 20
}