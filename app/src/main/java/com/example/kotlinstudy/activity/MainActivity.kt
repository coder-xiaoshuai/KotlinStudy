package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.utils.ToastUtils
import com.example.common.utils.ViewUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.MainStudyListAdapter
import com.example.kotlinstudy.bean.StudyListBean
import com.example.kotlinstudy.recyclerview.BaseRecyclerAdapter
import com.example.kotlinstudy.topactivity.TopActivity
import com.example.kotlinstudy.utils.DateUtils
import com.example.kotlinstudy.view.ClickSpanTextView
import com.example.kotlinstudy.view.helper.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BaseRecyclerAdapter.OnItemClickListener<StudyListBean> {
    private var mBackPressed: Long = 0
    private val TIME_INTERVAL = 2000  //再按一次退出

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        Log.i("MainActivity","结果"+DateUtils.isAdult("2001-09-23"))
        Log.i("MainActivity","结果"+DateUtils.isAdult("2002-09-24"))
        Log.i("MainActivity","结果"+DateUtils.isAdult("2002-09-25"))
        Log.i("MainActivity","结果"+DateUtils.isAdult("2001-08-23"))
        Log.i("MainActivity","结果"+DateUtils.isAdult("2002-09-26"))
        Log.i("MainActivity","结果"+DateUtils.isAdult("2002-09-25"))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    private fun initView() {
        text_hello_world.text =
            "学习kotlin的第${DateUtils.getDaysAfterSomeDay("2020-07-24", "yyyy-MM-dd")}天"
        text_hello_world.setClickText("${DateUtils.getDaysAfterSomeDay("2020-07-24", "yyyy-MM-dd")}")

        text_hello_world.setOnClickListener {
            if (text_hello_world.mClickHandled) {
                // 若已处理则直接返回
                text_hello_world.mClickHandled = false;
                return@setOnClickListener
            }

            ToastUtils.show("学习kotlin的第${DateUtils.getDaysAfterSomeDay("2020-07-24", "yyyy-MM-dd")}天")
        }

        text_hello_world.setTextClickListener(object : ClickSpanTextView.TextClickListener {
            override fun onTextClick() {
                text_hello_world.mClickHandled = true
                ToastUtils.show("hello world!")
            }
        })

        val categoryList = ArrayList<StudyListBean>()
        categoryList.add(StudyListBean("玩安卓推荐公众号", ContextCompat.getColor(this,R.color.color_card1)))
        categoryList.add(StudyListBean("仿其他应用效果", ContextCompat.getColor(this,R.color.color_card2)))
        categoryList.add(StudyListBean("Kotlin-->语法", ContextCompat.getColor(this,R.color.color_card3)))
        categoryList.add(StudyListBean("Kotlin-->协程", ContextCompat.getColor(this,R.color.color_card4)))
        categoryList.add(StudyListBean("flutter", ContextCompat.getColor(this,R.color.color_card5)))
        categoryList.add(StudyListBean("自定义view显示", ContextCompat.getColor(this,R.color.color_card6)))
        categoryList.add(StudyListBean("TopActivity", ContextCompat.getColor(this,R.color.color_card7)))
        categoryList.add(StudyListBean("预留分类", ContextCompat.getColor(this,R.color.color_card8)))
        categoryList.add(StudyListBean("question", ContextCompat.getColor(this,R.color.color_card9)))
        val mainAdapter = MainStudyListAdapter(this,categoryList)
        mainAdapter.setOnItemClickListener(this)
        rv_study_list.layoutManager = GridLayoutManager(this, 2)
        rv_study_list.addItemDecoration(GridSpacingItemDecoration(2, ViewUtils.dpToPx(8f), true))
        rv_study_list.adapter = mainAdapter
    }


    //统一处理点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.btn_test_trans -> {
//                val intent = Intent(this, TranslucentActivity::class.java)
//                startActivity(intent)
//            }
        }
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            ToastUtils.show("再按一次退出程序")
            mBackPressed = System.currentTimeMillis()
        }
    }

    override fun onItemClick(position: Int, itemData: StudyListBean?) {
        when (position) {
            0 -> {
                val intent = Intent(this, AuthorListActivity::class.java)
                startActivity(intent)

            }
            1 -> {
                val intent = Intent(this, SimulateOtherActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this, GrammarStudyActivity::class.java)
                startActivity(intent)

            }
            3 -> {
                val intent = Intent(this, CoroutineEasyActivity::class.java)
                startActivity(intent)
            }
            4 -> {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
                ToastUtils.show("暂未开放该功能")
            }
            5 -> {
                val intent = Intent(this, ShowCustomActivity::class.java)
                startActivity(intent)
            }
            6 -> {
                val intent = Intent(this, TopActivity::class.java)
                startActivity(intent)
            }

            7 -> {
                val intent = Intent(this, MobileInfoActivity::class.java)
                startActivity(intent)
            }

            8 -> {
                val intent = Intent(this, InputQuestionActivity::class.java)
                startActivity(intent)
            }
            else -> {
                ToastUtils.show("暂未添加分类")
            }
        }
    }
}