package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction

interface IMainScreenFragmentInput {
    fun showAccountsList(accounts: Array<Account>)
    fun showBalance(account: Account)
    fun setExchRate(data: String)
    fun showBalance(course: Double, account: Account)
    fun changePieChartData(transactions: List<FinanceTransaction>)
}