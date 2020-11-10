package com.example.kotlinstudy.mvvm

import me.hgj.jetpackmvvm.callback.livedata.UnPeekLiveData
import me.hgj.jetpackmvvm.network.manager.NetState

class NetworkStateManager private constructor() {
    val mNetWorkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }
}