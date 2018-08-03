package io.alekseimartoyas.financetracker.utils

import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal
import java.math.RoundingMode

fun Currency.toTargetCurrency(
        target: Currency,
        amount: BigDecimal,
        course: BigDecimal): BigDecimal =
        when (this) {
            Currency.RUB -> when (target) {
                Currency.RUB -> amount
                Currency.USD -> amount.divide(course, 2, RoundingMode.HALF_UP)
            }
            Currency.USD -> when (target) {
                Currency.RUB -> amount.multiply(course)
                Currency.USD -> amount
            }
        }