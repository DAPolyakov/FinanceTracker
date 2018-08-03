package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.interactors.AddFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.router.IAddTransactionRouter
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionPresenter(view: IAddTransactionActivityInput,
                              val addFinanceTransactionInteractor: AddFinanceTransactionInteractor,
                              val getAccountsInteractor: GetAccountsInteractor) :
        BasePresenter<IAddTransactionActivityInput,
                IAddTransactionRouter>(view) {

    override fun onStart() {
        getAccountsInteractor.execute {
            view?.setAccountsList(it)
        }
    }

    fun onAddFinanceTransaction(financeTransaction: FinanceTransaction) {
        addFinanceTransactionInteractor.execute(financeTransaction) {
            view?.back()
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
}