package com.example.kotlinstudy.view

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.kotlinstudy.R

open class ClickSpanTextView : AppCompatTextView {
    private var textClickListener: TextClickListener? = null
    private var clickText: String? = null
    private var clickTextColor: Int = Color.BLUE
    var mClickHandled: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ClickSpanTextView, defStyleAttr, 0)
        clickText = typedArray.getString(R.styleable.ClickSpanTextView_clickText)
        clickTextColor =
            typedArray.getColor(R.styleable.ClickSpanTextView_clickTextColor, Color.BLUE)
        typedArray?.recycle()

        highlightColor = ContextCompat.getColor(context, android.R.color.transparent)
        //这个一定要记得设置，不然点击不生效
        movementMethod = LinkMovementMethod.getInstance()
        generateSpan()
    }


    fun setClickText(clickText: String) {
        this.clickText = clickText
        generateSpan()
    }


    fun setTextClickListener(textClickListener: TextClickListener) {
        this.textClickListener = textClickListener
    }

    interface TextClickListener {
        fun onTextClick()
    }

    private fun generateSpan() {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(clickText)) {
            return
        }
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(clickText.toString())
        val endIndex = startIndex + (clickText?.length ?: 0)
        if (startIndex == endIndex) {
            return
        }
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                textClickListener?.onTextClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = clickTextColor
                ds.isUnderlineText = false
                ds.clearShadowLayer()
            }
        }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannableString
    }

}