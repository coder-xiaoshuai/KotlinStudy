package com.example.kotlinstudy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.bean.Chapter
import kotlinx.android.synthetic.main.item_rv_recommend_author.view.*

class AuthorListAdapter(var context: Context, var list: ArrayList<Chapter>? = null) :
    RecyclerView.Adapter<AuthorListAdapter.AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        return AuthorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_author, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        with(holder.itemView) {
            author_name.text = list?.get(position)?.name
        }
    }

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}