package com.amandafarrell.www.postpartumtracking

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun getFormattedDateString(systemTime: Long, format: String): String {
    return SimpleDateFormat(format)
        .format(systemTime).toString()
}