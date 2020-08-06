package com.example.kotlinstudy.bean

class ArticleWrapper {
    var currentPage: Int = 1
    var datas: List<Article>? = null
    var over: Boolean = false
    var total: Int = 0
}