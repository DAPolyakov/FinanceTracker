package io.alekseimartoyas.financetracker.presentation.modules.statistics.view

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface StatisticsView {
    fun setAccountsList(accounts: ArrayList<Account>)
    fun showStatistics(transactions: List<FinanceTransaction>)
}