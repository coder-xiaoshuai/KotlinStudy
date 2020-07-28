package com.example.kotlinstudy.base

import android.app.Application
import com.example.common.utils.CommonSpUtils
import com.example.common.utils.GlobalConfig
import com.example.common.utils.ToastUtils

class KotlinStudyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化Toast
        ToastUtils.init(this)
        GlobalConfig.setContext(this)
        //初始化Sp
        CommonSpUtils.init(this)
    }
}