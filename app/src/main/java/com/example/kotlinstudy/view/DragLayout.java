package com.example.kotlinstudy.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.customview.widget.ViewDragHelper;

import com.example.kotlinstudy.R;

public class DragLayout extends RelativeLayout {

    protected ViewDragHelper mViewDragHelper;
    private final Point mPoint = new Point();
    
    public DragLayout(Context context) {
        super(context);
        init();
    }
    
    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new mViewGragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }
    
    private int mDragViewId;
    private View mView;
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragViewId = R.id.drag_id;
        mView = findViewById(mDragViewId);
        mPoint.x = mView.getLeft();
        mPoint.y = mView.getTop();
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
    
    private final class mViewGragCallback extends ViewDragHelper.Callback {
    
        public mViewGragCallback() {
        }
    
        @Override
        public boolean tryCaptureView(final View child, int pointerId) {
            return child.getId() == mDragViewId;
        }
    
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //取得左边界的坐标
            final int leftBound = getPaddingLeft();
            //取得右边界的坐标
            final int rightBound = getWidth() - child.getWidth() - leftBound;
            //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
            //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
            //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
            return Math.min(Math.max(left, leftBound), rightBound);
        }
    
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), bottomBound);
        }
    
    
        @Override
        public int getViewHorizontalDragRange(View child) {
            if(child.getId() == mDragViewId) {
                return mWidth;
            }
            return 0;
        }
    
        @Override
        public int getViewVerticalDragRange(View child) {
            if(child.getId() == mDragViewId) {
                return mHeight;
            }
            return 0;
        }
    
        /*@Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            //在onEdgeDragStarted回调方法中，主动通过captureChildView对其进行捕获，该方法可以绕过tryCaptureView，所以我们的tryCaptureView虽然并为返回true，但却不影响
            //注意如果需要使用边界检测需要添加上mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);。
            mViewDragHelper.captureChildView(mView, pointerId);
        }*/
    
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            onDragFinish(releasedChild, mDragViewId, mView);
        }
    }
    
    /**
     * 如果需要定制拖动view之后的位置，则需要重写此方法
     * @param releasedChild
     * @param dragId
     * @param dragView
     */
    protected void onDragFinish(View releasedChild, int dragId, View dragView) {
        //super.onViewReleased(releasedChild, xvel, yvel);//松手的时候 判断如果是这个view 就让他靠边停靠
        if(releasedChild.getId() == mDragViewId) {
            //这边代码你跟进去去看会发现最终调用的是startScroll这个方法 所以我们就明白还要在computeScroll方法里刷新
            // 拖动距离大于屏幕的一半右移，拖动距离小于屏幕的一半左移
            int left = releasedChild.getLeft();
            int top = releasedChild.getTop();
            int childWidth = releasedChild.getWidth();
            if (left + childWidth / 2 > mWidth / 2) {
                left = mWidth - childWidth;
    
            } else {
                left = 0;
            }
            mViewDragHelper.settleCapturedViewAt(left, top);
            invalidate();
            LayoutParams params = (LayoutParams) mView.getLayoutParams();
            params.addRule(ALIGN_PARENT_RIGHT, 0);
            params.addRule(ALIGN_PARENT_TOP, 0);
            params.addRule(BELOW, 0);
            params.leftMargin = left;
            params.topMargin = top;
            mView.setLayoutParams(params);
        }
    };
    
    protected int mWidth = -1;
    protected int mHeight = -1;
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mWidth == -1 || mHeight == -1) {
            mWidth = r - l;
            mHeight = b - t;
        }
    }
    
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}