package com.example.kotlinstudy.view.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * author：zhangshuai
 */
class GridSpacingWithBanner(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) :
    ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            //banner不处理
        } else {
            val calPosition = position - 1
            val column = calPosition % spanCount
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount
                if (calPosition < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                //去掉上面的分割距离
                if (calPosition < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            }
        }

    }

}