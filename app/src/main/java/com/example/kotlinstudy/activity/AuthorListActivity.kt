package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.utils.ViewUtils
import com.example.common_ui.base.BaseActivity
import com.example.common_ui.views.TopBar
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.AuthorListAdapter
import com.example.kotlinstudy.bean.Banner
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.view.helper.GridSpacingItemDecoration
import com.example.kotlinstudy.view.helper.GridSpacingWithBanner
import com.example.kotlinstudy.viewmodel.WanAndroidViewModel
import kotlinx.android.synthetic.main.activity_author_list.*

class AuthorListActivity : BaseActivity() {

    private lateinit var mAdapter:AuthorListAdapter
    private var authorList:ArrayList<PublicInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getRecommendData()
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

        val gridLayoutManager =  GridLayoutManager(this@AuthorListActivity,2)
        gridLayoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if (position == 0){
                    //banner占一整行
                    return 2
                }
                return 1
            }

        }
        rv_author_list.layoutManager = gridLayoutManager
        rv_author_list.addItemDecoration(GridSpacingWithBanner(2,ViewUtils.dpToPx(16f),true))
        authorList = ArrayList()
        mAdapter = AuthorListAdapter(this@AuthorListActivity, authorList as ArrayList<PublicInfo>)
        rv_author_list.adapter = mAdapter
    }

    /**
     * 获取公众号作者列表
     */
    private fun getRecommendData() {
        val viewModel = ViewModelProvider(this).get(WanAndroidViewModel::class.java)
        viewModel.getRecommendData()
        viewModel.authorLiveData.observe(this,
            Observer<List<PublicInfo>> {
                it?.let {
                    authorList?.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            })

        viewModel.bannerLiveData.observe(this,
            Observer<List<Banner>> {
                it?.let {
                    mAdapter.banners = it
                    mAdapter.notifyItemChanged(0)
                }
            })
    }
}