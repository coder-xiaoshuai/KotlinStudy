package com.example.kotlinstudy.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.common.utils.ToastUtils

@SuppressLint("OverrideAbstract")
class NotificationMonitor : NotificationListenerService() {


    override fun onCreate() {
        super.onCreate()
        ToastUtils.show("开启服务成功")
    }

    override fun onBind(intent: Intent?): IBinder? {
        ToastUtils.show("开启服务成功")
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        //收到通知时
        super.onNotificationPosted(sbn)
        Log.i(Companion.TAG, "StatusBarNotification")
    }

    companion object {
        private const val TAG = "NotificationMonitor"
    }
}