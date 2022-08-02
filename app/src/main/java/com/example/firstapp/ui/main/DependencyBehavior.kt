package com.example.firstapp.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.firstapp.R

class DependencyBehavior : CoordinatorLayout.Behavior<TextView> {
    var width: Int = 0

    var ivUserBg: ImageView? = null
    var rawTop: Int = Int.MAX_VALUE

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.apply {
            width = resources.displayMetrics.widthPixels;
        }
    }


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
//        println("Behavior-child:  ${child.toString()}")
//        println("Behavior-dependency:  ${dependency.toString()}")
        if (dependency.id == R.id.iv_user_bg) println("********")
        if (ivUserBg == null) {
            ivUserBg = parent.findViewById<ImageView>(R.id.iv_user_bg)
        }
        if (rawTop == Int.MAX_VALUE) {
            rawTop = dependency.top
        }


        return dependency is NestedScrollView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {

        val top = dependency.top;
        val left = dependency.left;

        val x = width - left - child.width;
        val y = top * 2;


        child.translationX = x.toFloat()
        child.translationY = y.toFloat()
//        setPosition(child, x, y);
        ivUserBg?.apply {
            this.scaleY = (1F*(dependency.top - rawTop) / this.height).toFloat()+1
            this.scaleX = (1F*(dependency.top - rawTop) / this.height).toFloat()+1

            println("!!!!!!${this.scaleY},${dependency.top},${this.height}")
        }

        return true
    }
}