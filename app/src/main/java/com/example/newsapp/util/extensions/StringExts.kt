package com.example.newsapp.util.extensions

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.formatPublishedAt(): String{
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
    val currentDate = sdf.format(Date())
    val thisDate = this.formatDate()
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    val yesterdayDate = sdf.format(cal.time)

    return when (currentDate) {
        thisDate -> this.formatTime()

        yesterdayDate -> "Yesterday"

        else -> thisDate
    }
}

fun String.formatDate(): String {

    val inFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

    if (TextUtils.isEmpty(this)) {
        return ""
    }

    return try {
        val date = inFormat.parse(this)
        outFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.formatTime(): String {

    val inFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    if (TextUtils.isEmpty(this)) {
        return ""
    }

    return try {
        val date = inFormat.parse(this)
        outFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}




