package com.example.kotlinstudy.adapter

import android.content.Context
import com.example.kotlinstudy.R
import com.example.kotlinstudy.recyclerview.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_rv_menu.view.*
import java.util.ArrayList

class MenuListAdapter(context: Context, list: ArrayList<String>) :
    BaseRecyclerAdapter<String>(context, list) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_rv_menu
    }

    override fun onBindItemData(holder: BaseViewHolder, data: String?) {
        holder.itemView.text_menu.text = data
    }
}