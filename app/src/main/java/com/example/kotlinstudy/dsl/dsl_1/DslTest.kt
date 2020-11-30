package com.example.kotlinstudy.dsl.dsl_1

class DslTest {

    fun createPerson() {
        val person = person {
            age = 27
            name = "张三"
            address{
                street = "天南地北"
            }
        }
    }

}