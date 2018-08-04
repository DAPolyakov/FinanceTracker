package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.interactors.AddFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.router.IAddTransactionRouter
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionPresenter(view: IAddTransactionActivityInput,
                              val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
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

    fun onAddFinanceTransaction(financeTransaction: FinanceTransaction) {
        id?.let {
            updateFinanceTransactionInteractor.executeFlowable(financeTransaction
                    .copy(id = it, state = FinanceTransactionState.Done)) {
                view?.back()
            }
            
            if (financeTransaction.timeFinish != financeTransaction.timeStart) {
                addFinanceTransactionInteractor.executeFlowable(financeTransaction) {}
            }
        } ?: addFinanceTransactionInteractor.executeFlowable(financeTransaction) {
            view?.back()
        }
    }

//    private fun addNewFinanceTransaction()

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
}