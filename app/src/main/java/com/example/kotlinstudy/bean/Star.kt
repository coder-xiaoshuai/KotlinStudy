package com.example.kotlinstudy.bean

/**
 * 明星类
 */
class Star {
    var name: String = ""
    var allFansCount: Float = 0f //总粉丝数
    var lastIncreaseFans: List<Increase>? = null

    class Increase {
        var dataStr: String? = null //日期
        var increaseCount: Float = 0f //新增数量
    }
}