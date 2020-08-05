package com.example.kotlinstudy.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.ArticleListAdapter
import com.example.kotlinstudy.bean.Article
import com.example.kotlinstudy.bean.ArticleWrapper
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.net.KotlinStudyApi
import kotlinx.android.synthetic.main.activity_article_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 文章列表页面
 */
class ArticleListActivity : BaseActivity() {

    private var id: Int = 0
    private var currentPage: Int = 1
    private var isEnd: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getIntExtra("id", 405)
        getArticleList(currentPage)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_article_list
    }

    /**
     * 获取公众号作者列表
     */
    private fun getArticleList(page: Int) {
        //网络请求  如果是请求玩安卓的可以直接使用api去使用 baseUrl已经写死  如果想自定义baseUrl可以直接使用KotlinStudyApi.singleCustomRequest
        KotlinStudyApi.api?.getArticleList(id, page)?.enqueue(object :
            Callback<BaseResult<ArticleWrapper>> {
            override fun onFailure(call: Call<BaseResult<ArticleWrapper>>, t: Throwable) {
                Log.i("zs", "请求失败")
            }

            override fun onResponse(call: Call<BaseResult<ArticleWrapper>>, response: Response<BaseResult<ArticleWrapper>>) {
                Log.i("zs", "请求成功")
                if (response.isSuccessful) {
                    rv_article_list.layoutManager = LinearLayoutManager(this@ArticleListActivity, LinearLayoutManager.VERTICAL, false)
                    rv_article_list.adapter = ArticleListAdapter(this@ArticleListActivity, response.body()?.data?.datas as ArrayList<Article>?)
                }
            }
        })
    }

}