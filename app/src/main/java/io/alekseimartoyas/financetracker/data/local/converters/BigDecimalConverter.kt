package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal


class BigDecimalConverter() {
    @TypeConverter
    fun fromBigDecimal(data: BigDecimal): String = data.toString()

    @TypeConverter
    fun toBigDecimal(data: String): BigDecimal = BigDecimal(data)
}