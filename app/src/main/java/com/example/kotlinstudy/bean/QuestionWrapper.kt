package com.example.kotlinstudy.bean

class QuestionWrapper {
    var currentPage: Int = 1
    var datas: List<Question>? = null
    var over: Boolean = false
    var total: Int = 0
}