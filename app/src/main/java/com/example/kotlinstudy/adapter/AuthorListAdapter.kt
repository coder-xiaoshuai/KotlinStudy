package com.example.kotlinstudy.adapter

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

class AuthorListAdapter(var context: Context, var list: ArrayList<PublicInfo>? = null) :
    RecyclerView.Adapter<AuthorListAdapter.AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        return AuthorViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_author, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        with(holder.itemView) {
            author_name.text = list?.get(position)?.name
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArticleListActivity::class.java)
            intent.putExtra(Constant.INTENT_KEY_ID, list?.get(position)?.id ?: 405)
            intent.putExtra(Constant.INTENT_KEY_AUTHOR, list?.get(position)?.name ?: "文章列表")
            context.startActivity(intent)
        }
    }

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}