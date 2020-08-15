package com.example.kotlinstudy.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class HeaderBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<RelativeLayout>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RelativeLayout, dependency: View): Boolean {
        Log.i(Companion.TAG, "dependency$dependency")
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RelativeLayout, dependency: View): Boolean {
        Log.i(Companion.TAG, "onDependentViewChanged")
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: RelativeLayout, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        Log.i(Companion.TAG, "onStartNestedScroll")
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: RelativeLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.i(Companion.TAG, "onNestedPreScroll")
        Log.i(Companion.TAG, "target:$target")
        Log.i(TAG, "dy:$dy")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    companion object {
        private const val TAG = "HeaderBehavior"
    }

}