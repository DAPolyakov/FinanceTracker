package io.alekseimartoyas.financetracker.domain.interactors

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

abstract class BaseInteractor<ResulType, ParametrType>(
        protected val jobScheduler: Scheduler,
        private val uiScheduler: Scheduler) {
    private var disposable: Disposable? = null

    protected open fun buildObservable(args: ParametrType?): Observable<ResulType>? = null
    protected open fun buildFlowable(args: ParametrType?): Flowable<ResulType>? = null

    fun execute(args: ParametrType?, subscriber: (ResulType) -> Unit) {
        disposable = buildObservable(args)?.run {
            subscribeOn(jobScheduler)
                    .observeOn(uiScheduler)
                    .subscribe {
                        subscriber(it)
                    }
        }
    }

    fun executeFlowable(args: ParametrType?, subscriber: (ResulType) -> Unit) {
        disposable = buildFlowable(args)?.run {
            subscribeOn(jobScheduler)
                    .observeOn(uiScheduler)
                    .subscribe { subscriber(it) }
        }
    }

    fun execute(subscriber: (ResulType) -> Unit) {
        execute(null, subscriber)
    }

    fun executeFlowable(subscriber: (ResulType) -> Unit) {
        executeFlowable(null, subscriber)
    }

    fun dispose() {
        disposable?.dispose()
    }
}