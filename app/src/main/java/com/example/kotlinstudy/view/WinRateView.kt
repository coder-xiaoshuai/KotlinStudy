package com.example.kotlinstudy.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinstudy.R
import kotlin.math.min

/**
 * Created by xiaoshuai on 2016/12/18.
 * 用于显示胜率的自定义view
 */
class WinRateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {
    /*-----------自定义属性的相关变量-----------*/
    private val mColorWin: Int
    private val mColorLose: Int
    private val mColorTie: Int
    private var mPercentWin: Float
    private var mPercentLose: Float
    private var mPercentTie: Float
    private val isStartAnimator: Boolean
    private var angleWinStart = 0f
    private var angleWinEnd = 0f
    private var angleWinTmp = 0f
    private var angleTieStart = 0f
    private var angleTieEnd = 0f
    private var angleTieTmp = 0f
    private var angleLoseStart = 0f
    private var angleLoseEnd = 0f
    private var angleLoseTmp = 0f
    private var rectF: RectF? = null
    private var mPaint: Paint? = null
    private var rectWin: View? = null
    private var rectTie: View? = null
    private var rectLose: View? = null
    private var textWinRate: TextView? = null
    private var textTieRate: TextView? = null
    private var textLoseRate: TextView? = null
    private var itemWinRate: LinearLayout? = null
    private var itemTieRate: LinearLayout? = null
    private var itemLoseRate: LinearLayout? = null

    /***
     * 进行一些初始化工作
     */
    private fun initPaintAndView() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        rectWin = findViewById(R.id.rect_rate_win)
        rectWin?.setBackgroundColor(mColorWin)
        rectTie = findViewById(R.id.rect_rate_tie)
        rectTie?.setBackgroundColor(mColorTie)
        rectLose = findViewById(R.id.rect_rate_lose)
        rectLose?.setBackgroundColor(mColorLose)
        textWinRate = findViewById(R.id.text_rate_win)
        textWinRate?.text = "胜率$mPercentWin%"
        textTieRate = findViewById(R.id.text_rate_tie)
        textTieRate?.text = "平局率$mPercentTie%"
        textLoseRate = findViewById(R.id.text_rate_lose)
        textLoseRate?.text = "失败率$mPercentLose%"
        itemWinRate = findViewById(R.id.item_rate_win)
        itemTieRate = findViewById(R.id.item_rate_tie)
        itemLoseRate = findViewById(R.id.item_rate_lose)
    }

    private fun initEvent() {
        itemWinRate!!.setOnClickListener(this)
        itemTieRate!!.setOnClickListener(this)
        itemLoseRate!!.setOnClickListener(this)
    }

    /**
     * 检查是否合法
     */
    private fun checkIsLegal() {
        if (mPercentWin + mPercentLose + mPercentTie != 100f) {
            try {
                throw Exception("三种情况之和必须是一百")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mPercentTie = DEFAULT_PERCENT
            mPercentLose = mPercentTie
            mPercentWin = mPercentLose
        }
    }

    /**
     * 根据角度计算每种情况的 起始结束角度
     */
    private fun calculateEachAngle() {
        angleWinStart = 270f
        angleWinTmp = 0f
        angleWinEnd = (if (mPercentWin == 0f) 0 else 360 * mPercentWin / 100) as Float
        angleTieStart = (270 + 360 * mPercentWin / 100) % 360
        angleTieTmp = 0f
        angleTieEnd = (if (mPercentTie == 0f) 0 else 360 * mPercentTie / 100) as Float
        angleLoseStart = (270 + 360 * (mPercentWin / 100 + mPercentTie / 100)) % 360
        angleLoseTmp = 0f
        angleLoseEnd = (if (mPercentLose == 0f) 0 else 360 * mPercentLose / 100) as Float
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        /*------------先绘制外层弧线------------*/
        val lineWidth = 20f
        mPaint!!.strokeWidth = 20f
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeCap = Paint.Cap.SQUARE
        if (rectF == null) {
            rectF = RectF(
                paddingLeft + lineWidth / 2,
                paddingTop + lineWidth / 2,
                width - 2 * paddingLeft - lineWidth / 2,
                height - 2 * paddingTop - lineWidth / 2
            )
        }
        //起始角度为270度
        if (isStartAnimator) {
            mPaint!!.color = mColorWin
            canvas.drawArc(rectF!!, angleWinStart, angleWinTmp, false, mPaint!!)
            mPaint!!.color = mColorTie
            canvas.drawArc(rectF!!, angleTieStart, angleTieTmp, false, mPaint!!)
            mPaint!!.color = mColorLose
            canvas.drawArc(rectF!!, angleLoseStart, angleLoseTmp, false, mPaint!!)
            Log.d(TAG, "-----angle_win_tmp$angleWinTmp")
        } else {
            mPaint!!.color = mColorWin
            canvas.drawArc(rectF!!, angleWinStart, angleWinEnd, false, mPaint!!)
            mPaint!!.color = mColorTie
            canvas.drawArc(rectF!!, angleTieStart, angleTieEnd, false, mPaint!!)
            mPaint!!.color = mColorLose
            canvas.drawArc(rectF!!, angleLoseStart, angleLoseEnd, false, mPaint!!)
        }
    }

    /**
     * 对自定义view进行测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //宽高都是wrap_content的时候 我们指定默认大小
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE)
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE)
        } else {
            val minSize = min(widthSpecSize, heightSpecSize)
            setMeasuredDimension(minSize, minSize)
        }
    }

    /**
     * 设置各种情况的百分比
     *
     * @param percentWin
     * @param percentTie
     * @param percentLose
     */
    fun setEachPercent(percentWin: Float, percentTie: Float, percentLose: Float) {
        mPercentWin = percentWin
        mPercentTie = percentTie
        mPercentLose = percentLose
        textWinRate?.text = "胜率$mPercentWin%"
        textTieRate?.text = "平局率$mPercentTie%"
        textLoseRate?.text = "失败率$mPercentLose%"
        //需要重新检验和计算
        checkIsLegal()
        calculateEachAngle()
    }

    /**
     * 开启动画效果
     */
    private fun startAnimation() {
        val animatorDuration = 500L
        val animatorWin = ValueAnimator.ofFloat(0f, angleWinEnd)
        animatorWin.duration = animatorDuration
        animatorWin.interpolator = LinearInterpolator()
        animatorWin.start()
        val animatorTie = ValueAnimator.ofFloat(0f, angleTieEnd)
        animatorTie.duration = animatorDuration
        animatorTie.interpolator = LinearInterpolator()
        val animatorLose = ValueAnimator.ofFloat(0f, angleLoseEnd)
        animatorLose.duration = animatorDuration
        animatorLose.interpolator = LinearInterpolator()
        animatorWin.start()
        animatorTie.start()
        animatorLose.start()
        animatorWin.addUpdateListener { valueAnimator -> //进行重绘形成动画效果
            angleWinTmp = valueAnimator.animatedValue as Float
            invalidate()
        }
        animatorLose.addUpdateListener { valueAnimator -> //进行重绘形成动画效果
            angleLoseTmp = valueAnimator.animatedValue as Float
            invalidate()
        }
        animatorTie.addUpdateListener { valueAnimator ->
            angleTieTmp = valueAnimator.animatedValue as Float
            invalidate()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.item_rate_win -> Toast.makeText(this.context, "查看胜率", Toast.LENGTH_SHORT).show()
            R.id.item_rate_tie -> Toast.makeText(this.context, "查看平局率", Toast.LENGTH_SHORT).show()
            R.id.item_rate_lose -> Toast.makeText(this.context, "查看失败率", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "WinRateView"

        /*---------默认的三种情况的颜色-----------*/
        private const val DEFAULT_COLOR_WIN = 0Xff0000
        private const val DEFAULT_COLOR_LOSE = 0X0000ff
        private const val DEFAULT_COLOR_TIE = 0X00ff00
        private const val DEFAULT_PERCENT = 100f / 3
        private const val DEFAULT_SIZE = 200 //默认尺寸
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.WinRateView)
        mColorWin = array.getColor(R.styleable.WinRateView_color_win, DEFAULT_COLOR_WIN)
        mColorLose = array.getColor(R.styleable.WinRateView_color_lose, DEFAULT_COLOR_LOSE)
        mColorTie = array.getColor(R.styleable.WinRateView_color_tie, DEFAULT_COLOR_TIE)
        mPercentWin = array.getFloat(R.styleable.WinRateView_win_percent, DEFAULT_PERCENT)
        mPercentLose = array.getFloat(R.styleable.WinRateView_lose_percent, DEFAULT_PERCENT)
        mPercentTie = array.getFloat(R.styleable.WinRateView_tie_percent, DEFAULT_PERCENT)
        isStartAnimator = array.getBoolean(R.styleable.WinRateView_is_startAnimator, false)
        checkIsLegal()
        calculateEachAngle()
        //是否开启动画
        if (isStartAnimator) {
            startAnimation()
        }
        array.recycle()
        val centerView = LayoutInflater.from(context).inflate(R.layout.layout_center_win_rate, this, false)
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(CENTER_IN_PARENT)
        addView(centerView, layoutParams)
        initPaintAndView()
        initEvent()
    }
}