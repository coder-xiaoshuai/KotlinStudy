package com.example.kotlinstudy.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val DAY_MILLISECOND = 1000 * 60 * 60 * 24

    fun getDaysAfterSomeDay(someDay: String, format: String): Int {
        var result = 0
        try {
            val format = SimpleDateFormat(format)
            val lastTime = format.parse(someDay).time
            val currentTime = System.currentTimeMillis()
            result = ((currentTime - lastTime) / DAY_MILLISECOND + 1).toInt()
        } catch (e: Exception) {

        }
        return result
    }
}