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

    /**
     * 是否成年
     */
    fun isAdult(birthDay: String?): Boolean {
        val dates = birthDay?.split("-")
        dates?.let {
            val calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR) - DataUtils.toInt(dates[0])
            if (year > 18) {
                return true
            } else if (year < 18) {
                return false
            }
            // 如果年相等，就比较月份 获取月份记得+1
            val month: Int = calendar.get(Calendar.MONTH) + 1 - DataUtils.toInt(dates[1])
            if (month > 0) {
                return true
            } else if (month < 0) {
                return false
            }
            // 如果月也相等，就比较天
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH) - DataUtils.toInt(dates[2])
            return day >= 0
        }
        return false
    }
}