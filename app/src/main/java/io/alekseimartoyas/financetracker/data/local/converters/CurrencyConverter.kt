package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.Currency


class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.toString()

    @TypeConverter
    fun toCurrency(data: String): Currency = Currency.valueOf(data)
}