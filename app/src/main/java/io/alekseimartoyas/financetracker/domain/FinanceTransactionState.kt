package io.alekseimartoyas.financetracker.domain

import io.alekseimartoyas.financetracker.R


enum class FinanceTransactionState(val strId: Int) {
    Canceled(R.string.transaction_canceled),
    Waiting(R.string.transaction_waiting),
    Done(R.string.transaction_done),
    Template(R.string.transaction_template),
}