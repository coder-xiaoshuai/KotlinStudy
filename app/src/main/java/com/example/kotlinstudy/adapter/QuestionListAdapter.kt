package com.example.kotlinstudy.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.activity.WebPageActivity
import com.example.kotlinstudy.bean.Question
import com.example.kotlinstudy.utils.TextHtmlUtils
import kotlinx.android.synthetic.main.item_rv_daily_question.view.*

class QuestionListAdapter(var context: Context, var list: ArrayList<Question>? = null) :
    RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_daily_question, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        with(holder.itemView) {
            text_question_title.text = list?.get(position)?.title ?: ""
            val desc = Html.fromHtml(list?.get(position)?.desc ?: "")
            text_question_desc.text = desc
            TextHtmlUtils.handHtmlLink(context,text_question_desc)
            text_question_author.text = "作者: ${list?.get(position)?.author ?: "未知"}"
            text_question_time.text = list?.get(position)?.niceShareDate ?: ""
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebPageActivity::class.java)
            intent.putExtra(WebPageActivity.INTENT_KEY_URL, list?.get(position)?.link)
            intent.putExtra(WebPageActivity.INTENT_KEY_TITLE, "")
            context.startActivity(intent)
        }
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}