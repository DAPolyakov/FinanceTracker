package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.*
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionPresenter(view: IAddTransactionActivityInput,
                              val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
                              val getExchRateInteractor: GetExchRateInteractor,
                              val addFinanceTransactionInteractor: AddFinanceTransactionInteractor,
                              val getAccountsInteractor: GetAccountsInteractor,
                              val getTemplateTransactionsInteractor: GetTemplateTransactionsInteractor) :
        BasePresenter<IAddTransactionActivityInput, IMainActivityRouterInput>(view) {

    private var id: Long? = null
    private var isTabletMode = false

    fun setTabletMode(isTabletMode: Boolean) {
        this.isTabletMode = isTabletMode
    }

    override fun onStart() {
        getAccountsInteractor.executeFlowable {
            view?.setAccountsList(it.toTypedArray())
            view?.loadTransaction()
        }

        if (isTabletMode) {
            getTemplateTransactionsInteractor.executeFlowable {
                view?.showTemplates(it)
            }
        }
    }

    fun onAddFinanceTransaction(
            financeTransaction: FinanceTransaction,
            accountCurrency: Currency,
            isTemplate: Boolean) {

        getExchRateInteractor.execute { it ->
            var sum = financeTransaction.currency.toTargetCurrency(
                    accountCurrency,
                    financeTransaction.quantity.toBigDecimal(),
                    it.Valute.USD.Value.toBigDecimal()
            )

            if (isTemplate) {
                addFinanceTransactionInteractor.executeFlowable(
                        Pair(financeTransaction.copy(state = FinanceTransactionState.Template), BigDecimal(0))) {}
            }

            if (financeTransaction.operationType == OperationType.DEBIT) {
                sum = sum.negate()
            }

            id?.let { id ->
                updateFinanceTransactionInteractor.executeFlowable(
                        Pair(financeTransaction.copy(id = id, state = FinanceTransactionState.Done),
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
        val date: Date = Calendar.getInstance().time

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
        transaction?.let { it ->
            if (transaction.state == FinanceTransactionState.Template) {
                view?.back()
            } else {
                updateFinanceTransactionInteractor.executeFlowable(Pair(transaction.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
                    view?.back()
                }
            }
        } ?: view?.back()
    }

    fun onDeleteTemplate(it: FinanceTransaction) {
        updateFinanceTransactionInteractor.executeFlowable(Pair(it.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {}
    }
}