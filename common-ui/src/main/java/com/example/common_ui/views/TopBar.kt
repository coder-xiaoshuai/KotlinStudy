package com.example.common_ui.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.common_ui.R

class TopBar : RelativeLayout {

    var leftIcon: ImageView? = null
    var leftTextView: TextView? = null
    var centerTitle: TextView? = null
    var rightIcon1: ImageView? = null
    var rightIcon2: ImageView? = null
    var rightIcon3: ImageView? = null
    var rightTextView: TextView? = null

    var topBarClickListener: TopBarClickListener? = null

    var leftIconResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                leftIcon?.visibility = View.VISIBLE
                leftIcon?.setImageResource(value)
            }
        }

    var leftIconMode: Int = 0

    enum class LeftIconMode(val mode: Int) {
        NONE(0), BACK(1) ,HIDE(2)
    }

    var title: String ?= ""
        set(value) {
            field = value
            centerTitle?.text = value
        }

    var leftText: String ?= ""
        set(value) {
            field = value
            leftTextView?.text = value
        }

    var rightText: String ?= ""
        set(value) {
            field = value
            rightTextView?.text = value
        }

    var rightIcon1ResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                rightIcon1?.visibility = View.VISIBLE
                rightIcon1?.setImageResource(value)
            }
        }

    var rightIcon2ResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                rightIcon2?.visibility = View.VISIBLE
                rightIcon2?.setImageResource(value)
            }
        }

    var rightIcon3ResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                rightIcon3?.visibility = View.VISIBLE
                rightIcon3?.setImageResource(value)
            }
        }

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TopBar)
        leftIconResId = array.getResourceId(R.styleable.TopBar_leftIcon,0)
        leftIconMode = array.getInt(R.styleable.TopBar_leftIconMode,1)
        title = array.getString(R.styleable.TopBar_title)
        leftText = array.getString(R.styleable.TopBar_leftText)
        rightText = array.getString(R.styleable.TopBar_rightText)
        rightIcon1ResId = array.getResourceId(R.styleable.TopBar_rightIcon1,0)
        rightIcon2ResId = array.getResourceId(R.styleable.TopBar_rightIcon2,0)
        rightIcon3ResId = array.getResourceId(R.styleable.TopBar_rightIcon3,0)
        array.recycle()

        initViews()
        initEvent()
    }

    private fun initViews() {
        inflate(context, R.layout.layout_top_bar, this)
        leftIcon = findViewById(R.id.left_icon)
        if (leftIconResId != 0) {
            leftIcon?.setImageResource(leftIconResId)
        }
        if (leftIconMode == LeftIconMode.HIDE.mode) {
            leftIcon?.visibility = View.GONE
        }
        leftTextView = findViewById(R.id.left_text)
        leftTextView?.text = leftText
        centerTitle = findViewById(R.id.center_title)
        centerTitle?.text = title
        rightTextView = findViewById(R.id.text_right)
        rightTextView?.text = rightText
        rightIcon1 = findViewById(R.id.right_icon1)
        if (rightIcon1ResId != 0) {
            rightIcon1?.setImageResource(rightIcon1ResId)
        }
        rightIcon2 = findViewById(R.id.right_icon2)
        if (rightIcon2ResId != 0) {
            rightIcon2?.setImageResource(rightIcon2ResId)
        }
        rightIcon3 = findViewById(R.id.right_icon3)
        if (rightIcon3ResId != 0) {
            rightIcon3?.setImageResource(rightIcon3ResId)
        }
    }

    private fun initEvent() {
        leftIcon?.setOnClickListener {
            if (leftIconMode == LeftIconMode.BACK.mode && topBarClickListener == null) {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }

        leftTextView?.setOnClickListener {
            topBarClickListener?.onLeftTextClick()
        }

        centerTitle?.setOnClickListener {
            topBarClickListener?.onCenterTitleClick()
        }

        rightTextView?.setOnClickListener {
            topBarClickListener?.onRightTextClick()
        }

        rightIcon1?.setOnClickListener {
            topBarClickListener?.onRightIcon1Click()
        }

        rightIcon2?.setOnClickListener {
            topBarClickListener?.onRightIcon2Click()
        }

        rightIcon3?.setOnClickListener {
            topBarClickListener?.onRightIcon3Click()
        }
    }

    interface TopBarClickListener{
        fun onLeftIconClick()
        fun onLeftTextClick()
        fun onCenterTitleClick()
        fun onRightTextClick()
        fun onRightIcon1Click()
        fun onRightIcon2Click()
        fun onRightIcon3Click()
    }

    /**
     * 仿照动画的监听方式 上方调用可以按需实现相应方法 不用全部实现
     */
    class TopBarClickAdapter : TopBarClickListener {
        override fun onLeftIconClick() {
        }

        override fun onLeftTextClick() {
        }

        override fun onCenterTitleClick() {
        }

        override fun onRightTextClick() {
        }

        override fun onRightIcon1Click() {
        }

        override fun onRightIcon2Click() {
        }

        override fun onRightIcon3Click() {
        }
    }

}