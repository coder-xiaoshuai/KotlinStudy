package com.example.kotlinstudy.view

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper
import com.example.kotlinstudy.R

class DragLayout : RelativeLayout {
    protected var mViewDragHelper: ViewDragHelper? = null
    private val mPoint = Point()

    constructor(context: Context?) : super(context) {
        initHelper()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initHelper()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initHelper()
    }

    private fun initHelper() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mViewGragCallback())
        mViewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL)
    }

    private var mDragViewId = 0
    private var mView: View? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        mDragViewId = R.id.drag_id
        mView = findViewById(mDragViewId)
        mPoint.x = mView?.left ?: 0
        mPoint.y = mView?.top ?: 0
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper!!.cancel()
            return false
        }
        return mViewDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper!!.processTouchEvent(event)
        return true
    }

    private inner class mViewGragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == mDragViewId
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            //取得左边界的坐标
            val leftBound = paddingLeft
            //取得右边界的坐标
            val rightBound = width - child.width - leftBound
            //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
            //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
            //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
            return Math.min(Math.max(left, leftBound), rightBound)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val topBound = paddingTop
            val bottomBound = height - child.height - topBound
            return Math.min(Math.max(top, topBound), bottomBound)
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return if (child.id == mDragViewId) {
                mWidth
            } else 0
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return if (child.id == mDragViewId) {
                mHeight
            } else 0
        }

        /*@Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            //在onEdgeDragStarted回调方法中，主动通过captureChildView对其进行捕获，该方法可以绕过tryCaptureView，所以我们的tryCaptureView虽然并为返回true，但却不影响
            //注意如果需要使用边界检测需要添加上mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);。
            mViewDragHelper.captureChildView(mView, pointerId);
        }*/
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            onDragFinish(releasedChild, mDragViewId, mView)
        }
    }

    /**
     * 如果需要定制拖动view之后的位置，则需要重写此方法
     * @param releasedChild
     * @param dragId
     * @param dragView
     */
    protected fun onDragFinish(releasedChild: View, dragId: Int, dragView: View?) {
        //super.onViewReleased(releasedChild, xvel, yvel);//松手的时候 判断如果是这个view 就让他靠边停靠
        if (releasedChild.id == mDragViewId) {
            //这边代码你跟进去去看会发现最终调用的是startScroll这个方法 所以我们就明白还要在computeScroll方法里刷新
            // 拖动距离大于屏幕的一半右移，拖动距离小于屏幕的一半左移
            var left = releasedChild.left
            val top = releasedChild.top
            val childWidth = releasedChild.width
            left = if (left + childWidth / 2 > mWidth / 2) {
                mWidth - childWidth
            } else {
                0
            }
            mViewDragHelper!!.settleCapturedViewAt(left, top)
            invalidate()
            val params =
                mView!!.layoutParams as LayoutParams
            params.addRule(ALIGN_PARENT_RIGHT, 0)
            params.addRule(ALIGN_PARENT_TOP, 0)
            params.addRule(BELOW, 0)
            params.leftMargin = left
            params.topMargin = top
            mView!!.layoutParams = params
        }
    }

    protected var mWidth = -1
    protected var mHeight = -1
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mWidth == -1 || mHeight == -1) {
            mWidth = r - l
            mHeight = b - t
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mViewDragHelper!!.continueSettling(true)) {
            invalidate()
        }
    }
}