package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.CategoryType


class CategoryTypeConverter {
    @TypeConverter
    fun fromCategoryType(categoryType: CategoryType): String = categoryType.toString()

    @TypeConverter
    fun toCategoryType(data: String): CategoryType = CategoryType.valueOf(data)

}