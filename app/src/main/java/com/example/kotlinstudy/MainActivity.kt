package com.example.kotlinstudy

import android.os.Bundle
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.utils.DateUtils
import com.example.kotlinstudy.view.ClickSpanTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    private fun initView() {
        text_hello_world.text =
            "学习kotlin的第${DateUtils.getDaysAfterSomeDay("2020-07-24", "yyyy-MM-dd")}天 hello world!"
        text_hello_world.setClickText("hello world!")

        text_hello_world.setOnClickListener {
            if (text_hello_world.mClickHandled) {
                // 若已处理则直接返回
                text_hello_world.mClickHandled = false;
                return@setOnClickListener
            }

            ToastUtils.show(
                "学习kotlin的第${DateUtils.getDaysAfterSomeDay(
                    "2020-07-24",
                    "yyyy-MM-dd"
                )}天"
            )
        }

        text_hello_world.setTextClickListener(object : ClickSpanTextView.TextClickListener {
            override fun onTextClick() {
                text_hello_world.mClickHandled = true
                ToastUtils.show("hello world!")
            }
        })
    }
}