package com.example.kotlinstudy.adapter

import android.content.Context
import com.example.kotlinstudy.R
import com.example.kotlinstudy.bean.StudyListBean
import com.example.kotlinstudy.recyclerview.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_main_study_category.view.*

class MainStudyListAdapter(context: Context, list: ArrayList<StudyListBean>? = null) :
    BaseRecyclerAdapter<StudyListBean>(context, list) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_main_study_category
    }

    override fun onBindItemData(holder: BaseViewHolder, data: StudyListBean?) {
        holder.itemView.tv_category.text = data?.category
        holder.itemView.item_study_category.setCardBackgroundColor(data?.color ?: 0)
    }
}