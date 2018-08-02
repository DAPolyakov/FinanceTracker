package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.Currency


class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.strId.toString()

    @TypeConverter
    fun toCurrency(data: String): Currency {
        for (currency in Currency.values()) {
            if (currency.strId.toString() == data) {
                return currency
            }
        }
        throw Exception("Unknown currency type")
    }
}