package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import io.alekseimartoyas.financetracker.data.local.converters.CategoryTypeConverter
import io.alekseimartoyas.financetracker.data.local.converters.CurrencyConverter
import io.alekseimartoyas.financetracker.data.local.converters.OperationTypeConverter
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType


@Entity
@TypeConverters(OperationTypeConverter::class, CategoryTypeConverter::class, CurrencyConverter::class)
data class FinanceTransaction(
        val operationType: OperationType,
        val quantity: Float,
        val currency: Currency,
        val category: CategoryType,
        val date: String,
        val accountId: Long,
        @PrimaryKey(autoGenerate = true) val id: Long? = null)