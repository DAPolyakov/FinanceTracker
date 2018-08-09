package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface IScheduledTransactionsInput {
    fun showTransactions(data: List<FinanceTransaction>)
}