package com.raspisanie.mai.ui.main.settings

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.raspisanie.mai.Screens
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import com.raspisanie.mai.common.CiceroneHolder
import com.raspisanie.mai.common.base.BasePresenter
import com.raspisanie.mai.controllers.BottomVisibilityController
import com.raspisanie.mai.extesions.getAuthState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class SettingsPresenter : BasePresenter<MvpView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val context : Context by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun back() {
        router?.exit()
    }
}