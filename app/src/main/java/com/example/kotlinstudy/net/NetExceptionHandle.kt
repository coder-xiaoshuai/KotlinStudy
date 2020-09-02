package com.example.kotlinstudy.net

import android.util.Log
import com.example.common.utils.ToastUtils
import kotlinx.coroutines.CoroutineExceptionHandler

object NetExceptionHandle {
    private const val TAG = "NetExceptionHandle"
    val printExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, throwable.toString())
    }


    val toastExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        ToastUtils.show(throwable.message)
    }
}