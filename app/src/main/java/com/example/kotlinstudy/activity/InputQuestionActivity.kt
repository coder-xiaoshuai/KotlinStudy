package com.example.kotlinstudy.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.db.AppDatabase
import com.example.kotlinstudy.db.bean.TableQuestion
import kotlinx.android.synthetic.main.activity_input_data.*
import kotlinx.coroutines.*

class InputQuestionActivity : BaseActivity() {
    private val mainScope = MainScope() + CoroutineName("LocalQuestionActivity")
    var questionLevel: Int = 0
    var questionLevelName: String = "初级"
    var questionType: Int = 0
    var questionTypeName: String = "单选"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rg_question_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_single -> {
                    questionType = 0
                    questionTypeName = "单选"
                    selectQuestionType()
                }

                R.id.rb_multiple -> {
                    questionType = 1
                    questionTypeName = "多选"
                    selectQuestionType()
                }

                R.id.rb_judgment -> {
                    questionType = 2
                    questionTypeName = "判断"
                    selectQuestionType()
                }
            }
        }

        rg_question_level.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_primary -> {
                    questionLevel = 0
                    questionLevelName = "初级"
                }

                R.id.rb_middle_level -> {
                    questionLevel = 1
                    questionLevelName = "中级"
                }

                R.id.rb_high_level -> {
                    questionLevel = 2
                    questionLevelName = "高级"
                }
            }
        }

        btn_insert.setOnClickListener {
            if (TextUtils.isEmpty(et_question_content.text)) {
                ToastUtils.show("问题描述不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_answer_a.text) || TextUtils.isEmpty(et_answer_b.text)) {
                ToastUtils.show("答案不能为空")
                return@setOnClickListener
            }
            val question = TableQuestion()
            question.content = et_question_content.text.toString()
            question.questionLevel = questionLevel
            question.questionLevelName = questionLevelName
            question.questionType = questionType
            question.questionTypeName = questionTypeName
            question.answers = getAnswers()
            question.answerAnalysis = et_question_analysis.text.toString()
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

    private fun selectQuestionType() {
        when (questionType) {
            0 -> {
                //单选
                et_answer_c.visibility = View.VISIBLE
                et_answer_d.visibility = View.VISIBLE
                et_answer_e.visibility = View.VISIBLE
                et_answer_f.visibility = View.VISIBLE
                et_answer_g.visibility = View.VISIBLE
            }
            1 -> {
                //多选
                et_answer_c.visibility = View.VISIBLE
                et_answer_d.visibility = View.VISIBLE
                et_answer_e.visibility = View.VISIBLE
                et_answer_f.visibility = View.VISIBLE
                et_answer_g.visibility = View.VISIBLE
            }
            2 -> {
                //判断
                et_answer_c.visibility = View.GONE
                et_answer_d.visibility = View.GONE
                et_answer_e.visibility = View.GONE
                et_answer_f.visibility = View.GONE
                et_answer_g.visibility = View.GONE
            }
        }
    }

    private fun getAnswers(): ArrayList<String> {
        val answers = ArrayList<String>()
        if (!TextUtils.isEmpty(et_answer_a.text)) {
            answers.add(et_answer_a.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_b.text)) {
            answers.add(et_answer_b.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_c.text)) {
            answers.add(et_answer_c.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_d.text)) {
            answers.add(et_answer_d.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_e.text)) {
            answers.add(et_answer_e.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_f.text)) {
            answers.add(et_answer_f.text.toString())
        }
        if (!TextUtils.isEmpty(et_answer_g.text)) {
            answers.add(et_answer_g.text.toString())
        }
        return answers
    }

}