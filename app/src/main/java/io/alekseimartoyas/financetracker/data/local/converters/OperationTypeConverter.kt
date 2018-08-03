package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.OperationType


class OperationTypeConverter {
    @TypeConverter
    fun fromOperationType(operationType: OperationType): String = operationType.toString()

    @TypeConverter
    fun toOperationType(data: String) = OperationType.valueOf(data)

}