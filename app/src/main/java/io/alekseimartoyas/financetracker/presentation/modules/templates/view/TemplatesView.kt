package io.alekseimartoyas.financetracker.presentation.modules.templates.view

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


interface TemplatesView {
    fun setRvAdapter(adapter: TemplatesRvAdapter)
    fun showTransactions(data: List<FinanceTransaction>)
}