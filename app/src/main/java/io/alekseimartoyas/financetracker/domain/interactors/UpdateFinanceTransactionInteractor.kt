package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal

class UpdateFinanceTransactionInteractor(private val dataSource: IDataSourceInput)
    : BaseInteractor<Unit, Pair<FinanceTransaction, BigDecimal>>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildFlowable(args: Pair<FinanceTransaction, BigDecimal>?): Flowable<Unit> =
            dataSource.updateFinanceTransaction(args!!.first, args.second)

}