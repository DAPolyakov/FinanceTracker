package io.alekseimartoyas.financetracker.domain

import io.alekseimartoyas.financetracker.R

enum class CategoryType(val strId: Int) {
    Salary(R.string.salary),
    Shopping(R.string.shopping),
    Education(R.string.education)
}

enum class StatisticsCategoryType(val strId: Int) {
    AllCategories(R.string.all_categories),
    Salary(R.string.salary),
    Shopping(R.string.shopping),
    Education(R.string.education)
}