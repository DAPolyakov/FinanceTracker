package io.alekseimartoyas.financetracker

import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.entity.FinanceCalculating
import org.junit.Assert
import org.junit.Test

class FinanceCalculatingUnitTest {

    @Test
    fun sumEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(0, OperationType.ENLISTMENT, 4F, Currency.RUB, "", ""),
                FinanceTransaction(0, OperationType.ENLISTMENT, 5F, Currency.RUB, "", ""))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(9F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun sumDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(0, OperationType.ENLISTMENT, 4F, Currency.RUB, "", ""),
                FinanceTransaction(0, OperationType.ENLISTMENT, 5F, Currency.USD, "", ""))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(14F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(0, OperationType.ENLISTMENT, 400F, Currency.RUB, "", ""),
                FinanceTransaction(0, OperationType.DEBIT, 5F, Currency.RUB, "", ""))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(395F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(0, OperationType.ENLISTMENT, 400F, Currency.RUB, "", ""),
                FinanceTransaction(0, OperationType.DEBIT, 5F, Currency.USD, "", ""))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(390F, logic.calculateTransactionsSum(Currency.RUB))
    }
}