package com.example.kotlinstudy.manager

import com.example.kotlinstudy.base.UnPeekLiveData
import com.example.kotlinstudy.net.NetState

class NetworkStateManager private constructor() {
    val mNetWorkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }
}