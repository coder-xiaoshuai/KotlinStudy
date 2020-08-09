package com.example.kotlinstudy.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import com.example.kotlinstudy.activity.WebPageActivity


object TextHtmlUtils {
    fun handHtmlLink(context: Context, textView: TextView) {
        textView.movementMethod = LinkMovementMethod.getInstance()
        val text = textView.text
        if (text is Spannable) {
            val end = text.length
            val spannable: Spannable = textView.text as Spannable
            val urlSpans = spannable.getSpans(0, end, URLSpan::class.java)
            if (urlSpans.isNullOrEmpty()) {
                return
            }
            val builder: SpannableStringBuilder = SpannableStringBuilder(text)
            urlSpans.forEach {
                val url = it.url
                if (url.indexOf("http:") == 0 || url.indexOf("https:") == 0) {
                    val customUrlSpan = CustomUrlSpan(context, url)
                    builder.setSpan(customUrlSpan, spannable.getSpanStart(it), spannable.getSpanEnd(it), Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    builder.removeSpan(it)
                }
            }
            textView.text = builder
        }

    }

    class CustomUrlSpan(val context: Context, private val url: String) :
        ClickableSpan() {
        override fun onClick(widget: View) {
            // 在这里可以做任何自己想要的处理
            val intent = Intent(context, WebPageActivity::class.java)
            intent.putExtra(WebPageActivity.INTENT_KEY_URL, url)
            context.startActivity(intent)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = Color.BLUE
            ds.isUnderlineText = false
        }
    }
}