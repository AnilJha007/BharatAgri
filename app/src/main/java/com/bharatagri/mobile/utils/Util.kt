package com.bharatagri.mobile.utils

import android.app.AlertDialog
import android.content.Context
import com.bharatagri.mobile.R
import dmax.dialog.SpotsDialog
import java.text.SimpleDateFormat
import java.util.*

object Util {
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

    fun getAlertDialog(context: Context, cancelable: Boolean = false): AlertDialog {
        return SpotsDialog.Builder()
            .setContext(context)
            .setTheme(R.style.DialogTheme)
            .setCancelable(cancelable)
            .build()
    }
}