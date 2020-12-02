package com.example.kotlinstudy.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.bean.EventShowDialog
import kotlinx.android.synthetic.main.activity_single_instance.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SingleInstanceActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_single_instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        btn_min.setOnClickListener {
            moveTaskToBack(true)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        moveTaskToBack(true)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showDialog(dialogEvent: EventShowDialog) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("默认标题") //设置标题
            .setMessage("默认文本信息") //设置内容
            .setCancelable(false) //设置是否可以点击对话框以外的地方消失
            .setNegativeButton("取消",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })

        val alertDialog = builder.create()

        alertDialog.show()

    }
}