package com.example.kotlinstudy.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.WebPageActivity
import com.example.kotlinstudy.bean.Article
import com.example.kotlinstudy.utils.GlideUtils
import kotlinx.android.synthetic.main.item_rv_recommend_article.view.*

class ArticleListAdapter(var context: Context, var list: ArrayList<Article>? = null) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.itemView) {
            text_article_title.text = list?.get(position)?.title ?: ""
            text_article_time.text = list?.get(position)?.niceShareDate ?: ""
            var imageUrl = list?.get(position)?.envelopePic ?: ""
            if (!TextUtils.isEmpty(imageUrl)) {
                image_cover.visibility = View.VISIBLE
                GlideUtils.loadNormal(context, image_cover, imageUrl)
            } else {
                image_cover.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(context,WebPageActivity::class.java)
            intent.putExtra(WebPageActivity.INTENT_KEY_URL,list?.get(position)?.link)
            context.startActivity(intent)
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}