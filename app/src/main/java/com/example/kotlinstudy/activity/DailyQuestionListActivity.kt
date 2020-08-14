package com.example.kotlinstudy.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.QuestionListAdapter
import com.example.kotlinstudy.bean.Question
import com.example.kotlinstudy.bean.QuestionWrapper
import com.example.kotlinstudy.utils.Constant
import com.example.kotlinstudy.viewmodel.WanAndroidViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_daily_question_list.*

/**
 * 每日一问列表页面
 */
class DailyQuestionListActivity : BaseActivity() {

    private var id: Int = 0
    private var currentPage: Int = 1
    private lateinit var questionListAdapter: QuestionListAdapter
    private lateinit var questionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        id = intent.getIntExtra(Constant.INTENT_KEY_ID, 405)
        getQuestionList()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_daily_question_list
    }

    private fun initView() {
        rv_question_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        questionList = ArrayList()
        questionListAdapter = QuestionListAdapter(this, questionList)
        rv_question_list.adapter = questionListAdapter

        refresh_layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                currentPage++
                getQuestionList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                currentPage = 1
                getQuestionList()
            }
        })
    }

    /**
     * 获取文章列表
     */
    private fun getQuestionList() {
        val wanAndroidViewModel = ViewModelProvider(this).get(WanAndroidViewModel::class.java)
        wanAndroidViewModel.getDailyQuestionList(currentPage)
        wanAndroidViewModel.questionLiveData.observe(this,
            Observer<QuestionWrapper> {
                refresh_layout.finishRefresh()
                refresh_layout.finishLoadMore()
                it?.let {
                    currentPage = it.curPage
                    if (it.curPage == 1) {
                        //刷新
                        questionList.clear()
                        refresh_layout.setEnableLoadMore(true)

                    }
                    if (!it.datas.isNullOrEmpty()) {
                        questionList.addAll(it.datas!!)
                        questionListAdapter.notifyDataSetChanged()
                    }
                    if (it.over) {
                        refresh_layout.setEnableLoadMore(false)
                    }
                }

            })
    }

}