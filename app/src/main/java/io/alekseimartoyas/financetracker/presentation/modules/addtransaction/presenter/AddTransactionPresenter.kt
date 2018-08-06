package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.AddFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.router.IAddTransactionRouter
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionPresenter(view: IAddTransactionActivityInput,
                              val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
                              val getExchRateInteractor: GetExchRateInteractor,
                              val addFinanceTransactionInteractor: AddFinanceTransactionInteractor,
                              val getAccountsInteractor: GetAccountsInteractor) :
        BasePresenter<IAddTransactionActivityInput,
                IAddTransactionRouter>(view) {

    private var id: Long? = null

    override fun onStart() {
        getAccountsInteractor.executeFlowable {
            view?.setAccountsList(it.toTypedArray())
            view?.loadTransaction()
        }
    }

    fun onAddFinanceTransaction(financeTransaction: FinanceTransaction, accountCurrency: Currency) {

        getExchRateInteractor.execute {
            var sum = financeTransaction.currency.toTargetCurrency(
                    accountCurrency,
                    financeTransaction.quantity.toBigDecimal(),
                    it.Valute.USD.Value.toBigDecimal()
            )

            if (financeTransaction.operationType == OperationType.DEBIT) {
                sum = sum.negate()
            }

            id?.let {
                updateFinanceTransactionInteractor.executeFlowable(
                        Pair(financeTransaction.copy(id = it, state = FinanceTransactionState.Done),
                                sum)
                ) {
                    view?.back()
                }

                if (financeTransaction.timeFinish != financeTransaction.timeStart) {
                    addFinanceTransactionInteractor.executeFlowable(Pair(financeTransaction, BigDecimal(0))) {}
                }
            } ?: addFinanceTransactionInteractor.executeFlowable(Pair(financeTransaction, sum)) {
                view?.back()
            }

        }
    }

    fun getDate(): String {
        var date: Date = Calendar.getInstance().time

        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

        return sdf.format(date)
    }

    override fun onStop() {

    }

    fun loadTransactionId(id: Long?) {
        this.id = id
    }

    fun cancelTransaction(transaction: FinanceTransaction?) {
        transaction?.let {
            updateFinanceTransactionInteractor.executeFlowable(Pair(transaction.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
                view?.back()
            }
        } ?: view?.back()
    }
}