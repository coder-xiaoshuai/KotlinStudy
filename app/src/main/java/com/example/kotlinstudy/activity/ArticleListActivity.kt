package com.example.kotlinstudy.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.ArticleListAdapter
import com.example.kotlinstudy.bean.Article
import com.example.kotlinstudy.bean.ArticleWrapper
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.net.KotlinStudyApi
import com.example.kotlinstudy.utils.Constant
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
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
    private lateinit var articleListAdapter: ArticleListAdapter
    private lateinit var articleList: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        id = intent.getIntExtra(Constant.INTENT_KEY_ID, 405)
        getArticleList()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_article_list
    }

    private fun initView() {
        var articleAuthor = intent.getStringExtra(Constant.INTENT_KEY_AUTHOR)
        if (TextUtils.isEmpty(articleAuthor)) {
            articleAuthor = "文章列表"
        }
        text_title.text = articleAuthor

        rv_article_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        articleList = ArrayList()
        articleListAdapter = ArticleListAdapter(this, articleList)
        rv_article_list.adapter = articleListAdapter

        refresh_layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                currentPage++
                getArticleList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                currentPage = 1
                getArticleList()
            }
        })
    }

    /**
     * 获取文章列表
     */
    private fun getArticleList() {
        //网络请求  如果是请求玩安卓的可以直接使用api去使用 baseUrl已经写死  如果想自定义baseUrl可以直接使用KotlinStudyApi.singleCustomRequest
        KotlinStudyApi.api?.getArticleList(id, currentPage)?.enqueue(object :
            Callback<BaseResult<ArticleWrapper>> {
            override fun onFailure(call: Call<BaseResult<ArticleWrapper>>, t: Throwable) {
                refresh_layout.finishRefresh()
                refresh_layout.finishLoadMore()
            }

            override fun onResponse(call: Call<BaseResult<ArticleWrapper>>, response: Response<BaseResult<ArticleWrapper>>) {
                refresh_layout.finishRefresh()
                refresh_layout.finishLoadMore()
                if (response.isSuccessful) {
                    response.body()?.data?.datas?.let {
                        if (currentPage == 1) {
                            refresh_layout.setEnableLoadMore(true)
                            articleList.clear()
                        }
                        articleList.addAll(it)
                        articleListAdapter.notifyDataSetChanged()
                    }
                    response.body()?.data?.let {
                        if (it.over) {
                            refresh_layout.setEnableLoadMore(false)
                        }
                    }
                }
            }
        })
    }

}