package com.example.kotlinstudy.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.utils.ScreenUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.CommonVpAdapter
import com.example.kotlinstudy.adapter.OptionListAdapter
import com.example.kotlinstudy.fragment.CommonFragment
import com.example.kotlinstudy.utils.GlideUtils
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_kaola.appbarLayout
import kotlinx.android.synthetic.main.activity_kaola.image_back
import kotlinx.android.synthetic.main.activity_kaola.image_photo
import kotlinx.android.synthetic.main.activity_kaola.text_title_nickname
import kotlinx.android.synthetic.main.activity_kaola.toolbar
import kotlinx.android.synthetic.main.activity_star.*
import kotlinx.coroutines.*

class StarDetailActivity : BaseActivity() {
    val mainScope = MainScope() + CoroutineName("StarDetailActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowTheme()
        GlideUtils.loadNormal(this,
            image_photo,
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599286812161&di=1f4414ffbed89ddf6613a81243511b2d&imgtype=0&src=http%3A%2F%2F00imgmini.eastday.com%2Fmobile%2F20180914%2F20180914010926_bfe9626a3dd401474e4343eb5db2afbc_2.jpeg")
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_star
    }

    override fun initStatusBarColor() {
//        super.initStatusBarColor()
    }

    private fun initView() {

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) < appBarLayout.totalScrollRange * 2 / 5) {
                if ("" != text_title_nickname.text?.toString()) {
                    text_title_nickname.text = ""
                }
                image_back.setImageResource(R.drawable.ic_back_white)
                text_title_nickname.text = ""
                toolbar.setBackgroundColor(changeAlpha(resources.getColor(R.color.white),
                    Math.abs(verticalOffset * 1.0f) / (appBarLayout.totalScrollRange * 2 / 5)))
            } else {
                image_back.setImageResource(R.drawable.ic_back_gray)
                text_title_nickname.text = "沈梦辰"
                toolbar.setBackgroundColor(resources.getColor(R.color.white))
            }
        })

        //使用行业
        val list1 = arrayListOf<String>("互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网",
            "互联网")
        val layoutManager1 = GridLayoutManager(this, 2)
        layoutManager1.orientation = GridLayoutManager.HORIZONTAL
        rv_occupation_list.layoutManager = layoutManager1
        rv_occupation_list.adapter = OptionListAdapter(this, list1)

        //产品类型
        val list2 =
            arrayListOf<String>("肖像图片", "形象代言", "商家活动", "自媒体", "肖像图片", "形象代言", "商家活动", "自媒体")
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_product_type_list.layoutManager = layoutManager2
        rv_product_type_list.adapter = OptionListAdapter(this, list2)

        val titles = arrayListOf<String>("基本信息", "产品介绍", "商业价值", "艺人档期")
        val fragmentList = arrayListOf<Fragment>(CommonFragment(),
            CommonFragment(),
            CommonFragment(),
            CommonFragment())
        viewpager_bottom.adapter = CommonVpAdapter(supportFragmentManager, fragmentList, titles)
        tab_layout.setupWithViewPager(viewpager_bottom)

    }


    /**
     * 设置全屏、状态栏 、虚拟导航键等
     */
    private fun initWindowTheme() {
        if (Build.VERSION.SDK_INT >= 21) {
            val option =
                (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.gray_f2)
            window.decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
//        //监听是否有虚拟键盘
//        val bottomBarHeight = ScreenUtils.getNavigationBarHeight(this)
//        mainScope.launch {
//            delay(100)
//            if (bottomBarHeight > 0 && ScreenUtils.isNavigationBarExist(this@StarDetailActivity)) {
//                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                    FrameLayout.LayoutParams.WRAP_CONTENT)
//                params.bottomMargin = bottomBarHeight
//                rootView.layoutParams = params
//            }
//        }

        // 适配异型屏
        if (Build.VERSION.SDK_INT > 28) {
            var appliedInset = false
            toolbar.setOnApplyWindowInsetsListener { _, insets ->
                val displayCutout = insets.displayCutout
                if (displayCutout != null) {
                    if (!appliedInset) {
                        val cutoutHeight = displayCutout.safeInsetTop
                        toolbar.layoutParams.apply {
                            height += cutoutHeight
                        }
                        appliedInset = true
                    }
                } else {
                    if (!appliedInset) {
                        toolbar.layoutParams.apply {
                            height += ScreenUtils.getStatusBarHeight(this@StarDetailActivity)
                        }
                        appliedInset = true
                    }
                }
                insets
            }
        } else {
            toolbar.layoutParams.apply {
                height += ScreenUtils.getStatusBarHeight(this@StarDetailActivity)
            }
        }

    }

    /**
     * 设置透明度
     */
    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}