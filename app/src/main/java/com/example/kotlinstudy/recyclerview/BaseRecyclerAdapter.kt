package com.example.kotlinstudy.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.ArticleListActivity
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.utils.Constant
import kotlinx.android.synthetic.main.item_rv_recommend_author.view.*

abstract class BaseRecyclerAdapter<T>(var context: Context, var list: ArrayList<T>? = null) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(context).inflate(getItemId(), parent, false))
    }

    abstract fun getItemId(): Int

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemData(holder, list?.get(position))
    }

    /**
     * 如果只是简单绑定数据 无需position可以重写该方法
     */
    protected fun onBindItemData(holder: BaseViewHolder, data: T?) {

    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


    fun setDataList(dataList: ArrayList<T>?) {
        if (!dataList.isNullOrEmpty()) {
            this.list = dataList
            notifyDataSetChanged()
        }
    }

    fun addDataList(dataList: ArrayList<T>?) {
        if (!dataList.isNullOrEmpty()) {
            val startPosition = this.list?.size?.minus(1) ?: 0
            if (startPosition != -1) {
                this.list?.addAll(dataList)
                notifyItemRangeInserted(startPosition, dataList.size)
            }
        }
    }

    fun remove(data: T) {
        val removeIndex = this.list?.indexOf(data) ?: -1
        if (removeIndex != -1) {
            this.list?.remove(data)
            notifyItemRemoved(removeIndex)
        }
    }
}