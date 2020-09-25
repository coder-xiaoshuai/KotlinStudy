package com.example.kotlinstudy.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDex
import com.example.common.utils.CommonSpUtils
import com.example.common.utils.GlobalConfig
import com.example.common.utils.ToastUtils
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.MainActivity
import com.facebook.stetho.Stetho
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.util.*


class KotlinStudyApplication : Application() {

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.gray_f2, R.color.color_text_gray_66) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    private val activityStack = LinkedList<Activity>()

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        //初始化Toast
        ToastUtils.init(this)
        GlobalConfig.setContext(this)
        //初始化Sp
        CommonSpUtils.init(this)
        registerLifeCallback()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private var activityCount : Int = 0
    private fun registerLifeCallback(){
        registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
                Log.i("zs","onActivityPaused$activity")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.i("zs", "onActivityStarted$activity")
                if (activityCount <= 0) {
                    //应用进入前台
                }
                activityCount++
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i("zs","onActivityDestroyed$activity")
                activityStack.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.i("zs","onActivitySaveInstanceState$activity")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.i("zs","onActivityStopped$activity")
                activityCount--
                if (activityCount <= 0) {
                    //应用进入后台
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i("zs","onActivityCreated$activity")
                activityStack.add(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i("zs","onActivityResumed$activity")
            }
        })
    }
}