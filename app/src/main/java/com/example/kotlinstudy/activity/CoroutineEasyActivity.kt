package com.example.kotlinstudy.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_coroutine_easy.*
import kotlinx.coroutines.*

class CoroutineEasyActivity : BaseActivity() {

    private var job1: Job? = null

    private val mainScope = MainScope() + CoroutineName("CoroutineEasyActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_show_toast.setOnClickListener(this)
        btn_show_toast2.setOnClickListener(this)
        btn_show_toast3.setOnClickListener(this)
        btn_with_context.setOnClickListener(this)
        btn_run_blocking.setOnClickListener(this)
        btn_print_test.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_coroutine_easy
    }

    private fun delayShowToast() {
        /**
         * Global scope 通常用于启动顶级协程，这些协程在整个应用程序生命周期内运行，不会被过早地被取消。
         * 程序代码通常应该使用自定义的协程作用域。直接使用 GlobalScope 的 async 或者 launch 方法是强烈不建议的。
         */
        job1?.cancel()
        job1 = GlobalScope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                //模仿耗时
                delay(3000)
                "网络请求成功"
            }
            val result = deferred.await()
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
        }

    }


    /**
     * Kotlin 已经给我们提供了合适的协程作用域 MainScope
     *
     * aysnc()函数与launch()函数相同，唯一的区别是它有返回值，因为async {}返回的是 Deferred 类型
     */
    private fun delayShowToast2() {
        mainScope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                //模仿耗时
                delay(3000)
                "网络请求成功2"
            }
            val result = deferred.await()
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
        }

    }


    /**
     * Kotlin 已经给我们提供了合适的协程作用域 MainScope
     */
    private fun delayShowToast3() {
       mainScope.launch(Dispatchers.Main) {
            val deferred1 = async(Dispatchers.IO) {
                //模仿耗时
                delay(1000)
                "result1"
            }

            val deferred2 = async(Dispatchers.IO) {
                //模仿耗时
                delay(500)
                "result2"
            }
            val result = deferred1.await() + deferred2.await()
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 输出结果是1234
     */
    private fun print1234() {
        mainScope.launch {
            coroutineScope {
                delay(1000)
                Log.i("test", "1")
                launch {
                    delay(6000)
                    Log.i("test", "3")
                }
                Log.i("test", "2")
                return@coroutineScope
            }
            Log.i("test", "4")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_show_toast -> {
                delayShowToast()
            }

            R.id.btn_show_toast2 -> {
                delayShowToast2()
            }

            R.id.btn_show_toast3 -> {
                delayShowToast3()
            }

            R.id.btn_with_context -> {
                newSingleThreadContext("Ctx1").use { ctx1 ->
                    newSingleThreadContext("Ctx2").use { ctx2 ->
                        runBlocking(ctx1) {
                            withContext(ctx2) {
                            }
                        }
                    }
                }
            }

            R.id.btn_run_blocking -> {
               runBlocking {
                   delay(10000)
               }
            }
            R.id.btn_print_test ->{
                print1234()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job1?.cancel()
        mainScope.cancel()
    }
}