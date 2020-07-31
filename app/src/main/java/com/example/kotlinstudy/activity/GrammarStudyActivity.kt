package com.example.kotlinstudy.activity

import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R

/**
 * 练习kotlin语法页
 */
class GrammarStudyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_grammar_study
    }
}