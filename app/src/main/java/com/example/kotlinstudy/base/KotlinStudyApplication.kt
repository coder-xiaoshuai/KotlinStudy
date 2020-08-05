package com.example.kotlinstudy.base

import android.app.Application
import com.example.common.utils.CommonSpUtils
import com.example.common.utils.GlobalConfig
import com.example.common.utils.ToastUtils
import com.example.kotlinstudy.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


class KotlinStudyApplication : Application() {

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.gray_f2, R.color.white) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()
        //初始化Toast
        ToastUtils.init(this)
        GlobalConfig.setContext(this)
        //初始化Sp
        CommonSpUtils.init(this)
    }
}