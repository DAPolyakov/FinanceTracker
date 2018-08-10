package io.alekseimartoyas.financetracker

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.entity.FinanceCalculating
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import org.junit.Assert
import org.junit.Test
import java.math.RoundingMode


class FinanceCalculatingUnitTest {

    @Test
    fun rub_to_rub(){
        val  rub= Currency.RUB
        val amount = 100.toBigDecimal()
        val course = 10.toBigDecimal()
        Assert.assertEquals(amount, rub.toTargetCurrency(Currency.RUB, amount, course))
    }

    @Test
    fun rub_to_usd(){
        val  rub= Currency.RUB
        val amount = 100.toBigDecimal()
        val course = 10.toBigDecimal()
        Assert.assertEquals("10.00", rub.toTargetCurrency(Currency.USD, amount, course).toString())
    }

    @Test
    fun usd_to_usd(){
        val  usd= Currency.USD
        val amount = 100.toBigDecimal()
        val course = 10.toBigDecimal()
        Assert.assertEquals(amount, usd.toTargetCurrency(Currency.USD, amount, course))
    }

    @Test
    fun sumEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 4F, Currency.RUB, CategoryType.Shopping, "", 1, 1, 2, FinanceTransactionState.Done),
                FinanceTransaction(OperationType.ENLISTMENT, 5F, Currency.RUB, CategoryType.Salary, "", 1, 1, 2, FinanceTransactionState.Done))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(9F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun sumDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 4F, Currency.RUB, CategoryType.Shopping, "", 1, 1, 2, FinanceTransactionState.Done),
                FinanceTransaction(OperationType.ENLISTMENT, 5F, Currency.USD, CategoryType.Salary, "", 1, 1, 2, FinanceTransactionState.Done))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(14F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subEqualCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 400F, Currency.RUB, CategoryType.Shopping, "", 1, 1, 2, FinanceTransactionState.Done),
                FinanceTransaction(OperationType.DEBIT, 5F, Currency.RUB, CategoryType.Salary, "", 1, 1, 2, FinanceTransactionState.Done))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(395F, logic.calculateTransactionsSum(Currency.RUB))
    }

    @Test
    fun subDifferentCurrencies() {
        val transactions = arrayOf(FinanceTransaction(OperationType.ENLISTMENT, 400F, Currency.RUB, CategoryType.Salary, "", 1, 1, 2, FinanceTransactionState.Done),
                FinanceTransaction(OperationType.DEBIT, 5F, Currency.USD, CategoryType.Shopping, "", 1, 1, 2, FinanceTransactionState.Done))
        val logic = FinanceCalculating(transactions)
        Assert.assertEquals(390F, logic.calculateTransactionsSum(Currency.RUB))
    }
}