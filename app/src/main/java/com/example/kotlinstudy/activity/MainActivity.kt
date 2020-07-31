package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.bean.Chapter
import com.example.kotlinstudy.net.KotlinStudyApi
import com.example.kotlinstudy.utils.DateUtils
import com.example.kotlinstudy.view.ClickSpanTextView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initButtons()
        apiTest()
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

    private fun initButtons(){
        btn_kotlin_study.setOnClickListener(this)
    }

    private fun apiTest() {
        KotlinStudyApi.api?.requestChapters()?.enqueue(object :
            Callback<BaseResult<List<Chapter>>> {
            override fun onFailure(call: Call<BaseResult<List<Chapter>>>, t: Throwable) {
                Log.i("zs", "请求失败")
            }

            override fun onResponse(call: Call<BaseResult<List<Chapter>>>, response: Response<BaseResult<List<Chapter>>>) {
                Log.i("zs", "请求成功")
            }
        })
    }


    //统一处理点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_kotlin_study -> {
                val intent = Intent(this, GrammarStudyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}