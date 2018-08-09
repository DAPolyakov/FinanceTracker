package io.alekseimartoyas.financetracker.presentation.modules.history.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction

interface IHistoryFragmentInput {
    fun showTransactions(data: List<FinanceTransaction>)
}