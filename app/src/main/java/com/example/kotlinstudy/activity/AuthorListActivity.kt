package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.common_ui.views.TopBar
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.AuthorListAdapter
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.net.KotlinStudyApi
import com.example.kotlinstudy.viewmodel.WanAndroidViewModel
import kotlinx.android.synthetic.main.activity_author_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAuthorList()
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_author_list
    }

    private fun initView(){
        topBar.topBarClickListener = object : TopBar.TopBarClickListenerAdapter(){
            override fun onRightTextClick() {
                val intent = Intent(this@AuthorListActivity, DailyQuestionListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * 获取公众号作者列表
     */
    private fun getAuthorList() {
        val viewModel = ViewModelProvider(this).get(WanAndroidViewModel::class.java)
        viewModel.getAuthorList()
        viewModel.authorLiveData.observe(this,
            Observer<List<PublicInfo>> {
                it?.let {
                    rv_author_list.layoutManager = LinearLayoutManager(this@AuthorListActivity, LinearLayoutManager.VERTICAL, false)
                    rv_author_list.adapter = AuthorListAdapter(this@AuthorListActivity, it as ArrayList<PublicInfo>)
                }
            })
    }
}