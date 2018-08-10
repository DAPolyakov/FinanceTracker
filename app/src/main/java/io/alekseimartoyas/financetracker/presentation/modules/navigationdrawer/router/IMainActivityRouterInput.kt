package io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface IMainActivityRouterInput {
    fun showSettings()
    fun showAddTransaction(financeTransaction: FinanceTransaction? = null)
    fun returnFromAddAccount()
}