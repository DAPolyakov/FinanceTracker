package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal

@Entity
@TypeConverters(BigDecimal::class, Currency::class)
data class Account(
        val title: Int,
        val currency: Currency,
        val amount: BigDecimal,
        @Ignore val transactions: List<Transaction>,
        @PrimaryKey(autoGenerate = true) val id: Long? = null
)