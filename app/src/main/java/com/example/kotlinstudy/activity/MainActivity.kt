package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.DateUtils
import com.example.kotlinstudy.view.ClickSpanTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var mBackPressed: Long = 0
    private val TIME_INTERVAL = 2000  //再按一次退出

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initButtons()
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

    private fun initButtons() {
        btn_kotlin_study.setOnClickListener(this)
        btn_api_test.setOnClickListener(this)
        btn_custom_view.setOnClickListener(this)
//        btn_test_trans.setOnClickListener(this)
    }


    //统一处理点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_kotlin_study -> {
                val intent = Intent(this, GrammarStudyActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_api_test -> {
                val intent = Intent(this, AuthorListActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_custom_view -> {
                val intent = Intent(this,ShowCustomActivity::class.java)
                startActivity(intent)
            }

//            R.id.btn_test_trans -> {
//                val intent = Intent(this, TranslucentActivity::class.java)
//                startActivity(intent)
//            }
        }
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            ToastUtils.show("再按一次退出程序")
            mBackPressed = System.currentTimeMillis()
        }
    }
}