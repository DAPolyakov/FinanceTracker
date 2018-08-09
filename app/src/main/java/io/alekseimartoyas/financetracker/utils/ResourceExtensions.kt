package io.alekseimartoyas.financetracker.utils

import android.content.Context
import io.alekseimartoyas.financetracker.R

fun Context.isTabletMode(): Boolean = resources.getBoolean(R.bool.is_tablet)