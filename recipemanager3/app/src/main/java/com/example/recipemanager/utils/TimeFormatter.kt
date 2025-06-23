package com.example.recipemanager.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    fun formatDate(millis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date(millis))
    }
}
