package com.example.kotlinstudy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.ArticleListActivity
import com.example.kotlinstudy.bean.Article
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.utils.Constant
import kotlinx.android.synthetic.main.item_rv_recommend_author.view.*

class ArticleListAdapter(var context: Context, var list: ArrayList<Article>? = null) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_author, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.itemView) {
            author_name.text = list?.get(position)?.title
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}