package com.example.kotlinstudy.dsl.dsl_4

import java.text.SimpleDateFormat
import java.util.*

data class Person(val name: String,
    val dateOfBirth: Date,
    val addresses: List<Address>)


data class Address(val street: String,
    val number: Int,
    val city: String)


class PersonBuilder {

    var name: String = ""

    private var dob: Date = Date()
    var dateOfBirth: String = ""
        set(value) {
            dob = SimpleDateFormat("yyyy-MM-dd").parse(value)
        }

    private val addresses = mutableListOf<Address>()

//    fun address(block: ADDRESSES.() -> Unit) {
//        addresses.add(AddressBuilder().apply(block).build())
//    }


    fun addresses(block: ADDRESSES.() -> Unit) {
        addresses.addAll(ADDRESSES().apply(block))
    }

    fun build(): Person = Person(name, dob, addresses)

}

class ADDRESSES: ArrayList<Address>() {

    fun address(block: AddressBuilder.() -> Unit) {
        add(AddressBuilder().apply(block).build())
    }

}


class AddressBuilder {

    var street: String = ""
    var number: Int = 0
    var city: String = ""

    fun build() : Address = Address(street, number, city)

}

fun person(block: PersonBuilder.() -> Unit): Person = PersonBuilder().apply(block).build()

//调用测试

val p = person {
    name = "张三"
    name = "李四"  //后面的生效
    dateOfBirth = "2000-01-01"
    addresses {
        address {
            street = "长安街"
            city = "北京"
        }

        address {
            street = "朝阳街"
            city = "南京"
        }
    }
}