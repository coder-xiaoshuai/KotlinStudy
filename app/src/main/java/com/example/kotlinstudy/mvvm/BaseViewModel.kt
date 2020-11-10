package com.example.kotlinstudy.mvvm

import androidx.lifecycle.ViewModel

/**
 * 描述　: ViewModel的基类 使用ViewModel类，放弃AndroidViewModel，原因：用处不大 完全有其他方式获取Application上下文
 */
class BaseViewModel : ViewModel() {

    val loadingChange: UILoadingChange by lazy { UILoadingChange() }

    inner class UILoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }
}