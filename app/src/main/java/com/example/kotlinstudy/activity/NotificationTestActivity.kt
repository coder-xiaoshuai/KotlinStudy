package com.example.kotlinstudy.activity

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.widget.Toast
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.service.NotificationMonitor
import kotlinx.android.synthetic.main.activity_notification_test.*


class NotificationTestActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_request_permission.setOnClickListener {
            if (!notificationListenerEnable()) {
                gotoNotificationAccessSetting(this)
            } else {
                Toast.makeText(this, "已经有权限了", Toast.LENGTH_SHORT).show()
            }
        }

        btn_start_service.setOnClickListener {
            if (notificationListenerEnable()){
                var intent = Intent(this, NotificationMonitor::class.java)
                bindService(intent, object : ServiceConnection {
                    override fun onServiceDisconnected(name: ComponentName?) {

                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        ToastUtils.show("连接服务成功")
                    }
                }, Context.BIND_AUTO_CREATE)
            }else{
                Toast.makeText(this, "请先申请权限", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_notification_test
    }

    private fun gotoNotificationAccessSetting(context: Context): Boolean {
        return try {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            try {
                val intent = Intent()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val cn = ComponentName("com.android.settings",
                    "com.android.settings.Settings\$NotificationAccessSettingsActivity")
                intent.component = cn
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings")
                context.startActivity(intent)
                return true
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            false
        }
    }


    /**
     * 是否有具有监听通知权限
     */
    private fun notificationListenerEnable(): Boolean {
        var enable = false
        val packageName = packageName
        val flat: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        if (flat != null) {
            enable = flat.contains(packageName)
        }
        return enable
    }
}