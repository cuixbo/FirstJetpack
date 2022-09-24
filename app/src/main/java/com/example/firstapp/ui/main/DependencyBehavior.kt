package com.example.firstapp.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.firstapp.R
import com.google.android.material.tabs.TabLayout

class DependencyBehavior : CoordinatorLayout.Behavior<View> {
    var width: Int = 0

    var rawTop: Int = Int.MAX_VALUE

    var tabLayout: View? = null
    var userTop: View? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.apply {
            width = resources.displayMetrics.widthPixels;
        }
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        tryInitView(parent, dependency)
        return dependency is NestedScrollView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (rawTop == Int.MAX_VALUE) {
            rawTop = dependency.top
        }
        val top = dependency.top;

        tabLayout?.let {
            if (top <= rawTop) {
                it.translationY = 0F
            } else {
                it.translationY = (top - rawTop).toFloat()
            }
            println("onDependentViewChanged:${rawTop},${top},${it.translationY},")

        }
        userTop?.let {
            if (top <= rawTop) {
                it.translationY = 0F
            } else {
                it.translationY = (top - rawTop).toFloat()
            }
        }
        return true
    }

    private fun tryInitView(parent: CoordinatorLayout, dependency: View) {
        tabLayout ?: run {
            tabLayout = parent.findViewById(R.id.tl_tabs)
        }
        userTop ?: run {
            userTop = parent.findViewById(R.id.include_me_top_user)
        }
    }
}