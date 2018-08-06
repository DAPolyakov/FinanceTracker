package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState


class FinanceTransactionStateConverter {

    @TypeConverter
    fun fromTransactionState(state: FinanceTransactionState): String = state.toString()

    @TypeConverter
    fun toTransactionState(data: String) = FinanceTransactionState.valueOf(data)
}