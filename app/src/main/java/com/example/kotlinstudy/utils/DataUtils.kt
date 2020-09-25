package  com.example.kotlinstudy.utils

import android.text.TextUtils

object DataUtils {

    /**
     * 用这个工具类代替 sdk提供的 parseInt 或 toInt，
     */
    @JvmStatic
    fun toInt (input: String?): Int {
        return toInt(input, 0)
    }

    @JvmStatic
    fun toInt (input: String?, defaultValue: Int = 0): Int {
        var value = defaultValue

        if (!TextUtils.isEmpty(input)) try {
            value = Integer.parseInt(input!!)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }

    @JvmStatic
    fun toLong(input: String, defaultValue: Long = 0): Long {
        var value = defaultValue

        if (!TextUtils.isEmpty(input)) {
            try {
                value = input.toLongOrNull() ?: 0
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return value
    }

    @JvmStatic
    fun toDouble(input: String, defaultValue: Double = 0.0): Double {
        var value = defaultValue

        if (!TextUtils.isEmpty(input)) {
            try {
                value = input.toDoubleOrNull() ?: 0.0
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return value
    }

    @JvmStatic
    fun toFloat(input: String, defaultValue: Float = 0f): Float{
        var value = defaultValue

        if (!TextUtils.isEmpty(input)) {
            try {
                value = input.toFloatOrNull() ?: 0f
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return value
    }

    @JvmStatic
    fun toFloat(input: String): Float{
        return toFloat(input,0f)
    }
}
