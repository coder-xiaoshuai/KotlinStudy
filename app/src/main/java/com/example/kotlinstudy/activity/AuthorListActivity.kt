package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common_ui.base.BaseActivity
import com.example.common_ui.views.TopBar
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.AuthorListAdapter
import com.example.kotlinstudy.bean.Banner
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.viewmodel.WanAndroidViewModel
import kotlinx.android.synthetic.main.activity_author_list.*

class AuthorListActivity : BaseActivity() {

    private lateinit var mAdapter:AuthorListAdapter
    private var authorList:ArrayList<PublicInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getAuthorList()
        getBanners()
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

        rv_author_list.layoutManager = LinearLayoutManager(this@AuthorListActivity, LinearLayoutManager.VERTICAL, false)
        authorList = ArrayList()
        mAdapter = AuthorListAdapter(this@AuthorListActivity, authorList as ArrayList<PublicInfo>)
        rv_author_list.adapter = mAdapter
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
                    authorList?.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            })
    }

    /**
     * 获取banner数据
     */
    private fun getBanners(){
        val viewModel = ViewModelProvider(this).get(WanAndroidViewModel::class.java)
        viewModel.getBanners()
        viewModel.bannerLiveData.observe(this,
            Observer<List<Banner>> {
                it?.let {
                    mAdapter.banners = it
                    mAdapter.notifyItemChanged(0)
                }
            })
    }
}