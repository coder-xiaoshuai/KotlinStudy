package com.example.kotlinstudy.activity

import android.os.Bundle
import android.util.Log
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.db.AppDatabase
import com.example.kotlinstudy.db.bean.TableQuestion
import kotlinx.coroutines.*

class LocalQuestionActivity : BaseActivity() {
    val mainScope = MainScope() + CoroutineName("LocalQuestionActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val question = TableQuestion()
        question.questionId = 2
        question.answerAnalysis = "llll"
        question.content = "kjdslkjlskj"
        question.contentImageUrl = "ljkljl"
        question.answers = listOf("a", "b", "c")
        AppDatabase.inDb {
            AppDatabase.getDatabase(applicationContext).questionDao().insertQuestion(question)
        }

        mainScope.launch {
            Log.i("LocalQuestionActivity", Thread.currentThread().toString())
            async(Dispatchers.IO) {
                val question = TableQuestion()
                question.questionId = 2
                question.answerAnalysis = "llll"
                question.content = "kjdslkjlskj"
                question.contentImageUrl = "ljkljl"
                question.answers = listOf("a", "b", "c")
                AppDatabase.getDatabase(applicationContext).questionDao().insertQuestion(question)
            }
        }
    }
}