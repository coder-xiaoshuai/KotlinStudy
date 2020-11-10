package com.example.kotlinstudy.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.IllegalArgumentException
import java.util.*

/**
 * TODO：UnPeekLiveData 的存在是为了在 "重回二级页面" 的场景下，解决 "数据倒灌" 的问题。
 * 对 "数据倒灌" 的状况不理解的小伙伴，可参考《jetpack MVVM 精讲》的解析
 *
 *
 * https://juejin.im/post/5dafc49b6fb9a04e17209922
 *
 *
 * 本类参考了官方 SingleEventLive 的非入侵设计，
 *
 *
 * TODO：并创新性地引入了 "延迟清空消息" 的设计，
 * 如此可确保：
 * 1.一条消息能被多个观察者消费
 * 2.延迟期结束，不再能够收到旧消息的推送
 * 3.并且旧消息在延迟期结束时能从内存中释放，避免内存溢出等问题
 * 4.让非入侵设计成为可能，遵循开闭原则
 * 用于限制从 Activity/Fragment 推送数据，推送数据务必通过唯一可信源来分发，
 * 如果这样说还不理解，详见：
 * https://xiaozhuanlan.com/topic/6719328450 和 https://xiaozhuanlan.com/topic/0168753249
 */
open class EventLiveData<T> : MutableLiveData<T>() {
    private var isCleaning: Boolean = false
    private var hasHandled: Boolean = false
    private var isDelaying: Boolean = false
    protected var DELAY_TO_CLEAR_EVENT = 1000
    private val mTimer = Timer()
    private var mTask: TimerTask? = null
    protected var isAllowNullValue: Boolean = false
    protected var isAllowToClear: Boolean = true
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (isCleaning) {
                hasHandled = true
                isDelaying = true
                isCleaning = true
                return@Observer
            }
            if (!hasHandled) {
                hasHandled = true
                isDelaying = true
                observer.onChanged(it)
            } else if (isDelaying) {
                observer.onChanged(it)
            }
        })
    }

    /**
     * UnPeekLiveData 主要用于表现层的 页面转场 和 页面间通信 场景下的非粘性消息分发，
     * 出于生命周期安全等因素的考虑，不建议使用 observeForever 方法，
     *
     *
     * 对于数据层的工作，如有需要，可结合实际场景使用 MutableLiveData 或 kotlin flow。
     *
     * @param observer
     */
    override fun observeForever(observer: Observer<in T>) {
        throw IllegalArgumentException("Do not use observeForever for communication between pages to avoid lifecycle security issues")
    }

    override fun setValue(value: T) {
        if (!isCleaning && !isAllowNullValue && value == null) {
            return
        }
        hasHandled = false
        isDelaying = false
        super.setValue(value)
        mTask?.cancel()
        mTimer.purge()

        if (value != null) {
            mTask = object : TimerTask() {
                override fun run() {
                    clear()
                }
            }
            mTimer.schedule(mTask, DELAY_TO_CLEAR_EVENT.toLong())
        }
        mTask
    }

    private fun clear() {
        if (isAllowToClear) {
            isCleaning = true
            super.postValue(null)
        } else {
            hasHandled = true
            isDelaying = false
        }
    }

}