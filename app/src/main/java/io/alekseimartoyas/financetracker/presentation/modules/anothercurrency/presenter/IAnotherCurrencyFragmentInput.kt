package io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.presenter

import io.alekseimartoyas.financetracker.data.local.Account

interface IAnotherCurrencyFragmentInput {
    fun setExchRate(data: String)
    fun showBalance(course: Double, account: Account)
}