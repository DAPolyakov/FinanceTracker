package io.alekseimartoyas.financetracker.data.local.converters

import android.arch.persistence.room.TypeConverter
import io.alekseimartoyas.financetracker.domain.CategoryType


class CategoryTypeConverter {
    @TypeConverter
    fun fromCategory(categoryType: CategoryType): String = categoryType.strId.toString()

    @TypeConverter
    fun toCategoryType(data: String): CategoryType {
        for (category in CategoryType.values()) {
            if (category.strId.toString() == data) {
                return category
            }
        }
        throw Exception("Unknown category type")
    }

}