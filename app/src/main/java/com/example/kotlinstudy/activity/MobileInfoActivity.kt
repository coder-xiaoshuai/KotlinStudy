package com.example.kotlinstudy.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.mobile.mobilehardware.build.BuildHelper
import kotlinx.android.synthetic.main.activity_mobile_info.*
import org.json.JSONObject

class MobileInfoActivity : BaseActivity() {

    private val options = arrayListOf<String>("App信息","App安装列表","音量数据","版本数据","电池状态","Cpu实时数据","蓝牙数据","系统build数据","摄像头数据")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text_mobile_info.text = getKeyValueFromJson(BuildHelper.mobGetBuildInfo())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mobile_info
    }


    private fun openMore(){
        val view = LayoutInflater.from(this).inflate(R.layout.popup_window,null)

    }

    private fun getKeyValueFromJson(jsonObject: JSONObject): String {
        val stringBuilder = StringBuilder()
        jsonObject.keys().forEach {
            stringBuilder.append("$it : ${jsonObject.get(it)}\n")
        }
        return stringBuilder.toString()
    }
}