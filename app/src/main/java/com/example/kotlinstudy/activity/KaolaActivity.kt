package com.example.kotlinstudy.activity

import android.graphics.Color
import android.os.Bundle
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.GlideUtils
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_kaola.*
@Deprecated("没有达到预期效果 暂时弃用了")
class KaolaActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlideUtils.loadNormal(this,image_photo,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597406198489&di=592002a12f8be90ab6725ab3824f7e30&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F54fbb2fb43166d22d552b941432309f79052d23a.jpg")
//        GlideUtils.loadNormal(this,image_bottom,"https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3128011664,408345996&fm=26&gp=0.jpg")
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kaola
    }

    private fun initView(){

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) < appBarLayout.totalScrollRange * 2 / 5) {
                if ("" != text_title_nickname.text?.toString()) {
                    text_title_nickname.text = ""
                }
                image_back.setImageResource(R.drawable.ic_back_white)
                toolbar.setBackgroundColor(changeAlpha(resources.getColor(R.color.white), Math.abs(verticalOffset * 1.0f) / (appBarLayout.totalScrollRange * 2 / 5)))
//                image_nav_right.setImageResource(if (isSelf) R.drawable.yidui_img_navi_right_edit_n else R.drawable.yidui_img_navi_right_n)
            } else {
                image_back.setImageResource(R.drawable.ic_back_gray)
                toolbar.setBackgroundColor(resources.getColor(R.color.white))
//                image_nav_right.setImageResource(if (isSelf) R.drawable.yidui_img_navi_right_edit_p else R.drawable.yidui_img_navi_right_p)
            }
//            divider.visibility = if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) View.VISIBLE else View.GONE
        })

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
}