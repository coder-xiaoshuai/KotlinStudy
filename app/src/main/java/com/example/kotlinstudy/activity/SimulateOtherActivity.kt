package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_simulate_other.*

class SimulateOtherActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_simulate_kaola.setOnClickListener(this)
        btn_simulate_fanji.setOnClickListener(this)
        btn_simulate_duiyuan.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_simulate_other
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_simulate_kaola -> {
                val intent = Intent(this, KaolaActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_simulate_fanji ->{
                val intent = Intent(this, LineChartActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_simulate_duiyuan ->{
                val intent = Intent(this, VideoGuideActivity2::class.java)
                startActivity(intent)
            }
        }
    }
}