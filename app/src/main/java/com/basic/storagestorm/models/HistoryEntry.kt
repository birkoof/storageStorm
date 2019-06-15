package com.basic.storagestorm.models

import java.text.SimpleDateFormat
import java.util.*

class HistoryEntry(val entry: String, val date: Long) {

    fun getDateAsString(): String {
        val dateObject = Date(date)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return format.format(dateObject)
    }
}