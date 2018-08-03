package io.alekseimartoyas.financetracker.utils


fun Int.daysToMillis(): Long {
    return this * 24 * 60 * 60 * 1000L
}