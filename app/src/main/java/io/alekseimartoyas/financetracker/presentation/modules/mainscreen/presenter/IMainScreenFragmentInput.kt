package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.Account

interface IMainScreenFragmentInput {
    fun showAccountsList(accounts: Array<Account>)
    fun showBalance(account: Account)
}