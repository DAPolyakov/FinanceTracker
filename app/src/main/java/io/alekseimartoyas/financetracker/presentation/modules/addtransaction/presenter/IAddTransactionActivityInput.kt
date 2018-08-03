package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter

import io.alekseimartoyas.financetracker.data.local.Account


interface IAddTransactionActivityInput {
    fun setAccountsList(accounts: Array<Account>)
    fun back()
}