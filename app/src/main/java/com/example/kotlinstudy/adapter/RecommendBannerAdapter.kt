package com.example.kotlinstudy.adapter

import android.text.TextUtils
import android.view.View
import com.example.kotlinstudy.R
import com.example.kotlinstudy.bean.Banner
import com.example.kotlinstudy.utils.GlideUtils
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import kotlinx.android.synthetic.main.item_banner.view.*

class RecommendBannerAdapter:BaseBannerAdapter<Banner,RecommendBannerAdapter.RecommendBannerViewHolder> (){

    class RecommendBannerViewHolder(itemView: View?) : BaseViewHolder<Banner>(itemView!!) {
        override fun bindData(data: Banner?, position: Int, pageSize: Int) {
            if (!TextUtils.isEmpty(data?.imagePath)) {
                GlideUtils.loadNormal(itemView.context, itemView.banner_image, data!!.imagePath!!)
            }
            itemView.banner_text.text = data?.title
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }

    override fun createViewHolder(itemView: View?, viewType: Int): RecommendBannerViewHolder {
        return RecommendBannerViewHolder(itemView)
    }

    override fun onBind(holder: RecommendBannerViewHolder?, data: Banner?, position: Int, pageSize: Int) {
        holder?.bindData(data, position, pageSize)
    }
}