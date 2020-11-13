package com.example.kotlinstudy.activity

import android.os.Bundle
import com.example.kotlinstudy.R
import com.example.kotlinstudy.base.BaseViewModel
import com.example.kotlinstudy.base.KtBaseActivity
import com.example.kotlinstudy.databinding.ActivityNetStateBindBinding
import com.example.kotlinstudy.net.NetState

class NetworkChangeActivity : KtBaseActivity<BaseViewModel, ActivityNetStateBindBinding>() {
    override fun layoutId() = R.layout.activity_net_state_bind

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    override fun createObserver() {
    }

    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        mDataBind.textNetState.text = "网络状态${netState.isSuccess}"
    }

}