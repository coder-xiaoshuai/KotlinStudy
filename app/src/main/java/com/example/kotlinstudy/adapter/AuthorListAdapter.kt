package com.example.kotlinstudy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.common.utils.ViewUtils
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.ArticleListActivity
import com.example.kotlinstudy.bean.Banner
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.utils.Constant
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.item_rv_recommend_author.view.*
import kotlinx.android.synthetic.main.item_rv_recommend_banner.view.*

class AuthorListAdapter(var context: Context, var list: ArrayList<PublicInfo>? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var banners: List<Banner>? = null

    companion object{
        private const val ITEM_TYPE_BANNER = 1
        private const val ITEM_TYPE_NORMAL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_BANNER){
            return BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_banner, parent, false))
        }
        return AuthorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_author, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size?.plus(1) ?: 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_TYPE_BANNER else ITEM_TYPE_NORMAL
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BannerViewHolder) {
            val bannerView:BannerViewPager<Banner, RecommendBannerAdapter.RecommendBannerViewHolder> =
                holder.itemView.banner_view as BannerViewPager<Banner, RecommendBannerAdapter.RecommendBannerViewHolder>
            if (bannerView.adapter == null && banners != null) {
                bannerView.apply {
                    adapter = RecommendBannerAdapter()
                    setAutoPlay(true)
                    if (context is AppCompatActivity) {
                        setLifecycleRegistry((context as AppCompatActivity).lifecycle)
                    }
                    setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                    setIndicatorSliderGap(ViewUtils.dpToPx(4f))
                    setIndicatorMargin(0, 0, 0, ViewUtils.dpToPx(100f).toInt())
                    setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                    setIndicatorSliderRadius(ViewUtils.dpToPx(3f), ViewUtils.dpToPx(4.5f))
                    setIndicatorSliderColor(ContextCompat.getColor(context, R.color.white),
                        ContextCompat.getColor(context, R.color.white))
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                        }
                    })

                }.create()
                bannerView.refreshData(banners!!)
            }

        } else if (holder is AuthorViewHolder) {
            var realPosition = position - 1
            with(holder.itemView) {
                author_name.text = list?.get(realPosition)?.name
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ArticleListActivity::class.java)
                intent.putExtra(Constant.INTENT_KEY_ID, list?.get(realPosition)?.id ?: 405)
                intent.putExtra(Constant.INTENT_KEY_AUTHOR, list?.get(realPosition)?.name ?: "文章列表")
                context.startActivity(intent)
            }
        }
    }

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}