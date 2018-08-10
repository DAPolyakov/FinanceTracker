package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface IAddTransactionActivityInput {
    fun setAccountsList(accounts: Array<Account>)
    fun back()
    fun loadTransaction()
    fun showTemplates(templates: List<FinanceTransaction>)
}