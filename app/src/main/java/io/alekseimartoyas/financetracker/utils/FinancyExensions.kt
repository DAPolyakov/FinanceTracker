package io.alekseimartoyas.financetracker.utils

import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal

fun Currency.toTargetCurrency(
        target: Currency,
        amount: BigDecimal,
        course: BigDecimal): BigDecimal =
        amount.multiply(course)