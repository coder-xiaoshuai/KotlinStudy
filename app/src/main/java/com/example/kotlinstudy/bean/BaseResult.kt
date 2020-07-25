package com.example.kotlinstudy.bean

import java.io.Serializable

class BaseResult<T> : Serializable {
    var data: T? = null
    var errorCode = 0
    var errorMsg: String? = null
}