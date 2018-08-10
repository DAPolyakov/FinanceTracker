package io.alekseimartoyas.financetracker.data

import io.alekseimartoyas.financetracker.data.local.converters.*
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal


class ConvertersUnitTests {

    @Test
    fun bigDecimalConverterTest() {
        val converter = BigDecimalConverter()
        val data = BigDecimal(3)
        val dataStr = "3"

        Assert.assertEquals(converter.fromBigDecimal(data), dataStr)
        Assert.assertEquals(converter.toBigDecimal(dataStr), data)
    }

    @Test
    fun categoryTypeConverterTest() {
        val converter = CategoryTypeConverter()
        val data = CategoryType.Salary
        val dataStr = "Salary"

        Assert.assertEquals(converter.fromCategoryType(data), dataStr)
        Assert.assertEquals(converter.toCategoryType(dataStr), data)
    }

    @Test
    fun currencyConverterTest() {
        val converter = CurrencyConverter()
        val data = Currency.USD
        val dataStr = "USD"

        Assert.assertEquals(converter.fromCurrency(data), dataStr)
        Assert.assertEquals(converter.toCurrency(dataStr), data)
    }

    @Test
    fun financeTransactionStateConverterTest() {
        val converter = FinanceTransactionStateConverter()
        val data = FinanceTransactionState.Waiting
        val dataStr = "Waiting"

        Assert.assertEquals(converter.fromTransactionState(data), dataStr)
        Assert.assertEquals(converter.toTransactionState(dataStr), data)
    }

    @Test
    fun operationTypeConverterTest() {
        val converter = OperationTypeConverter()
        val data = OperationType.ENLISTMENT
        val dataStr = "ENLISTMENT"

        Assert.assertEquals(converter.fromOperationType(data), dataStr)
        Assert.assertEquals(converter.toOperationType(dataStr), data)
    }

}