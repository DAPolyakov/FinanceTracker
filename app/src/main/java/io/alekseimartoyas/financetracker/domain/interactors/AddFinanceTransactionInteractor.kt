package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal


class AddFinanceTransactionInteractor(private val dataSource: IDataSourceInput,
                                      job: Scheduler = Schedulers.io(),
                                      ui: Scheduler = Schedulers.io())
    : BaseInteractor<Unit, Pair<FinanceTransaction, BigDecimal>>(job, ui) {

    override fun buildFlowable(args: Pair<FinanceTransaction, BigDecimal>?): Flowable<Unit>? {
        return dataSource.addTransaction(args!!.first, args.second)
    }
}