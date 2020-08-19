package com.example.kotlinstudy.utils

import com.example.kotlinstudy.bean.Star
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object StarUtils {
    fun getStar1():Star{
        val star1 = Star()
        star1.name = "张三丰"
        star1.allFansCount = 1001.2f
        star1.lastIncreaseFans = getIncreaseList()
        return star1
    }

    fun getStar2():Star{
        val star1 = Star()
        star1.name = "张翠山"
        star1.allFansCount = 1001.2f
        star1.lastIncreaseFans = getIncreaseList()
        return star1
    }

    fun getStar3():Star{
        val star1 = Star()
        star1.name = "张无极"
        star1.allFansCount = 1001.2f
        star1.lastIncreaseFans = getIncreaseList()
        return star1
    }


    private fun getIncreaseList(): List<Star.Increase> {
        val list = ArrayList<Star.Increase>()
        val currentTimeStamp = System.currentTimeMillis()
        for (i in 0..18) {
            val increase = Star.Increase()
            increase.dataStr =
                SimpleDateFormat("MM/dd").format(Date(currentTimeStamp - (18 - i) * 60 * 60 * 24 * 1000))
            val format = DecimalFormat("0.##")
            format.roundingMode = RoundingMode.FLOOR
            format.format(((java.lang.Math.random() + 0.1) * 10).toFloat())
            increase.increaseCount =
                format.format(((java.lang.Math.random() + 0.1) * 10).toFloat()).toFloat()
            list.add(increase)
        }
        return list
    }
}