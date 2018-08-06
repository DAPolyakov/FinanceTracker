package io.alekseimartoyas.financetracker.presentation.modules.history.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction

interface ITransactionRVInput {
    fun setData(transactions: Array<FinanceTransaction>)
}