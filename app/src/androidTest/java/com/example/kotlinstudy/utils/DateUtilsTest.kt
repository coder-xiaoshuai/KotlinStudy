package com.example.kotlinstudy.utils

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

class DateUtilsTest {

    @Test
    fun isAdult() {
        println(DateUtils.isAdult("2001-09-25"))
        println(DateUtils.isAdult("2002-09-24"))
        println(DateUtils.isAdult("2002-09-25"))
        println(DateUtils.isAdult("2002-09-26"))
        println(DateUtils.isAdult("2003-09-25"))
    }
}