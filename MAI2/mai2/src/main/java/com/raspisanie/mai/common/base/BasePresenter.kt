package com.raspisanie.mai.common.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.KoinComponent

open class BasePresenter<V : MvpView> : MvpPresenter<V>(), KoinComponent {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}