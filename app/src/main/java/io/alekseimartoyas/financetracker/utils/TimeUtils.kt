package io.alekseimartoyas.financetracker.utils


const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000L
const val MILLIS_IN_SECOND = 1000L

fun Int.daysToMillis(): Long {
    return this * MILLIS_IN_DAY
}

fun Int.secondsToMillis(): Long {
    return this * MILLIS_IN_SECOND
}

fun Long.millisToDays(): Long {
    return this / (MILLIS_IN_DAY)
}

fun Long.millisToSeconds(): Long {
    return this / MILLIS_IN_SECOND
}
