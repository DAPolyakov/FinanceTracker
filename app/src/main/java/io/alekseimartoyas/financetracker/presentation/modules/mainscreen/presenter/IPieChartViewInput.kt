package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction

interface IPieChartViewInput {
    fun changeData(data: List<FinanceTransaction>)

    fun destructor()
}