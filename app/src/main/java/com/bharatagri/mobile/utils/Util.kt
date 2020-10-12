package com.bharatagri.mobile.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDateFromString(dateString: String, format: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(dateString)
    } catch (e: Exception) {
        null
    }
}

fun getDateString(date: Date, format: String = "dd-MMM-yyyy"): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(date)
}
