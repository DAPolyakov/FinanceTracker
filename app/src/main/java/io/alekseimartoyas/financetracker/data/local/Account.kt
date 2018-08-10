package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import io.alekseimartoyas.financetracker.data.local.converters.BigDecimalConverter
import io.alekseimartoyas.financetracker.data.local.converters.CurrencyConverter
import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal


@Entity
@TypeConverters(BigDecimalConverter::class, CurrencyConverter::class)
data class Account(
        val title: String,
        val currency: Currency,
        val amount: BigDecimal,
        @PrimaryKey(autoGenerate = true) val id: Long? = null
)