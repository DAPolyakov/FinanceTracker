package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface IScheduledTransactionsRVInput {
    fun setData(transactions: Array<FinanceTransaction>)
    fun onDelete(onDelete: (FinanceTransaction) -> Unit)
}