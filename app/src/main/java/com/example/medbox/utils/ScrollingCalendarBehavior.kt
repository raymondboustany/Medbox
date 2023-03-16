package com.example.medbox.utils


import android.content.Context
import com.google.android.material.appbar.AppBarLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent

class ScrollingCalendarBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean {
        return false/*super.onInterceptTouchEvent(parent, child, ev)*/
    }
}
