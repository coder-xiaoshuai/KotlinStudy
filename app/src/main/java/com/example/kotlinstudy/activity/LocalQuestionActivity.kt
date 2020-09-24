package com.example.kotlinstudy.activity

import android.os.Bundle
import android.util.Log
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.db.AppDatabase
import com.example.kotlinstudy.db.bean.TableQuestion
import kotlinx.android.synthetic.main.activity_input_data.*
import kotlinx.coroutines.*

class LocalQuestionActivity : BaseActivity() {
    val mainScope = MainScope() + CoroutineName("LocalQuestionActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_insert.setOnClickListener {
            val question = TableQuestion()
            question.answerAnalysis = "答案解析"
            question.content = "问题描述"
            question.contentImageUrl = "图片路径"
            question.answers = listOf("a", "b", "c")
            AppDatabase.inDb {
                AppDatabase.getDatabase(applicationContext).questionDao().insertQuestion(question)
            }
        }

        btn_query.setOnClickListener {
            mainScope.launch {
                val result = async(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).questionDao().getAllQuestion()
                }.await()
                ToastUtils.show("插入了${result?.size}条数据")
                result?.forEach {
                    Log.i("question", "id:${it.questionId}")
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_input_data
    }
}