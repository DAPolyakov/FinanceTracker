package io.alekseimartoyas.financetracker

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.entity.FinanceCalculating
import org.junit.Assert
import org.junit.Test

class FinanceCalculatingUnitTest {

    @Test
    fun sumEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 4F, Currency.RUB, CategoryType.Category2, "", 1),
                FinanceTransaction(OperationType.ENLISTMENT, 5F, Currency.RUB, CategoryType.Category1, "", 1))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(9F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun sumDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 4F, Currency.RUB, CategoryType.Category2, "", 1),
                FinanceTransaction(OperationType.ENLISTMENT, 5F, Currency.USD, CategoryType.Category1, "", 1))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(14F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 400F, Currency.RUB, CategoryType.Category2, "", 1),
                FinanceTransaction(OperationType.DEBIT, 5F, Currency.RUB, CategoryType.Category1, "", 1))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(395F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 400F, Currency.RUB, CategoryType.Category1, "", 1),
                FinanceTransaction(OperationType.DEBIT, 5F, Currency.USD, CategoryType.Category2, "", 1))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(390F, logic.calculateTransactionsSum(Currency.RUB))
    }
}