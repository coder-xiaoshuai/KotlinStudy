package com.example.kotlinstudy.activity

import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class KotlinMapActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * list遍历
     */
    fun traversalArrayList() {
        val list = arrayListOf<Int>(1, 2, 3, 4, 5, 7, 9, 1)
        //普通遍历
        for (number in list) {
            println("数字${number}")
        }
        //遍历不需要index
        list.forEach {
            println("数字${it}")
        }

        //遍历需要使用index
        for ((index, number) in list.withIndex()) {
            println("数字${number},它的位置索引${index}")
        }

    }

    /**
     * 集合的函数式api - filter
     */
    fun filterTest() {
        val list = listOf(1, 2, 3, 4)
        println(list.filter { it % 2 == 0 })
    }

    /**
     * 集合的函数式api - map
     */
    fun mapTest() {
        val list = listOf(1, 2, 3, 4)
        println(list)
        val listString = list.map { "数字${it}" }
        println(listString)
    }

    /**
     * all any count find 对集合应用的判断式
     */
    fun checkTest() {
        val list = listOf("hello", "world!!", "all", "any", "count", "find")
        //判断所有的字符串是否长度都大于2
        list.all {
            it.length > 2
        }

        //判断是否包含了hello
        list.any {
            "hello" == it
        }

        //计数list中包含hello的个数
        val containsHello = { string: String -> "hello" == string }
        print(list.count(containsHello))

        list.find(containsHello)

    }

    /**
     * 把列表转换成分组的map
     */
    fun groupByTes() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7)
        val map = list.groupBy { it > 4 }

        val list2 = listOf("a", "abc", "aaa", "bit", "bat", "cat", "check")
        val map2 = list2.groupBy(String::first)
        val map3 = list2.groupBy { string: String -> string.first() }
    }


    fun flatMapTest() {
        val list = listOf("kotlin", "study")
        val result1 = list.map { string: String -> string.toList() }
        val result = list.flatMap { string: String -> string.toList() }
    }

    //todo 学习惰性序列

    /*-------协程相关--------*/
    fun coroutinesTest(){
        GlobalScope.launch {  }
        GlobalScope.async {  }
    }

}