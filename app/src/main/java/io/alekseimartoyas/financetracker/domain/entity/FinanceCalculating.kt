package io.alekseimartoyas.financetracker.domain.entity

import io.alekseimartoyas.financetracker.data.local.Transaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType

class FinanceCalculating(val transactions: Array<Transaction>) : FinanceCalculatingInput {

    override fun calculateTransactionsSum(inCurrency: Currency): Float {
        var balance = 0F

        for (item in transactions)
            if (item.operationType == OperationType.ENLISTMENT) {
                balance += toTargetCurrency(item, inCurrency, 2F)
            } else if (item.operationType == OperationType.DEBIT) {
                balance -= toTargetCurrency(item, inCurrency, 2F)
            }

        return balance
    }

    private fun toTargetCurrency(transaction: Transaction,
                                 target: Currency,
                                 course: Float): Float =
            if (transaction.currency == target) {
                transaction.quantity
            } else {
                transaction.quantity * course
            }
}