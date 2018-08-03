package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState


class FinanceTransactionStateConverter {

    @TypeConverter
    fun fromTransactionState(state: FinanceTransactionState): String = state.strId.toString()

    @TypeConverter
    fun toTransactionState(data: String): FinanceTransactionState {
        for (state in FinanceTransactionState.values()) {
            if (state.strId.toString() == data) {
                return state
            }
        }
        throw Exception("Unknown transaction state")
    }

}