package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_translucent.*

class TranslucentActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_translucent.setOnClickListener {
            val intent = Intent(this, TranslucentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_translucent
    }
}