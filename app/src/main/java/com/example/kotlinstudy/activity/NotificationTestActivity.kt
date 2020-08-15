package com.example.kotlinstudy.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.service.notification.NotificationListenerService
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_notification_test.*

class NotificationTestActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_start_service.setOnClickListener{
            var intent  = Intent(this,NotificationListenerService::class.java)
            bindService(intent,object :ServiceConnection{
                override fun onServiceDisconnected(name: ComponentName?) {

                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    ToastUtils.show("连接服务成功")
                }
            }, Context.BIND_AUTO_CREATE)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_notification_test
    }
}