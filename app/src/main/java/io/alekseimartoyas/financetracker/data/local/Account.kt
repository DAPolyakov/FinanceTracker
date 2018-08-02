package io.alekseimartoyas.financetracker.data.local

import io.alekseimartoyas.financetracker.domain.Currency


data class Account(
        val id: String,
        val title: Int,
        val currency: Currency,
        val transactions: List<Transaction>
)