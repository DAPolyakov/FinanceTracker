package io.alekseimartoyas.financetracker.data.local

import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal


data class Account(
        val id: String,
        val title: Int,
        val currency: Currency,
        val amount: BigDecimal,
        val transactions: List<Transaction>
)