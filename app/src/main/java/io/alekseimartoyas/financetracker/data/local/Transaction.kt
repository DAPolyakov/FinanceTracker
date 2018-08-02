package io.alekseimartoyas.financetracker.data.local

import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType

data class Transaction(val id: Int,
                       val operationType: OperationType,
                       val quantity: Float,
                       val currency: Currency,
                       val category: CategoryType,
                       val date: String)