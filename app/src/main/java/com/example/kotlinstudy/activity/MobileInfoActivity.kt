package com.example.kotlinstudy.activity

import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.mobile.mobilehardware.build.BuildHelper
import kotlinx.android.synthetic.main.activity_mobile_info.*

class MobileInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text_mobile_info.text = BuildHelper.mobGetBuildInfo().toString()
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_mobile_info
    }
}