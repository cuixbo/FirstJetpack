package com.example.firstapp.ui.main

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.example.firstapp.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field
import kotlin.math.max
import kotlin.math.min


/**
 * https://gist.github.com/kibotu/c1266c067543693c32d81eb5bcdcc5b2
 */
class AppBarLayoutOverScrollViewBehavior(context: Context?, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {


    private var targetHeight = 500f
    private var targetView: View? = null
    private var parentHeight = 0
    private var targetViewHeight = 0
    private var totalDy = 0f
    private var lastScale = 0f
    private var lastBottom = 0
    private var isAnimate = false

    private var tabLayout: TabLayout? = null
    private var tabLayoutHeight: Int = 0
    private val tabLayoutItem = ScrollItems()
    private val userTopItem = ScrollItems()


    init {
        attrs?.let {
            // todo get overscroll view by id
            // todo get height multiplier
        }
    }


    override fun onLayoutChild(
        parent: CoordinatorLayout,
        abl: AppBarLayout,
        layoutDirection: Int
    ): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (targetView == null) {
            targetView = parent.findViewWithTag(TAG)
            tabLayout = parent.findViewById(R.id.tl_tabs)
            val userTopView: View? = parent.findViewById(R.id.include_me_top_user)
            tabLayout?.let {
                tabLayoutItem.itemView = it
                tabLayoutItem.itemHeight = it.height
            }
            userTopView?.let {
                userTopItem.itemView = it
                userTopItem.itemHeight = it.height
            }

            if (targetView != null) {
                initial(abl)
            }
        }

        if (targetView == null) {
            throw NullPointerException("No target view defined, please set tag to 'overscroll'")
        }

        targetHeight = targetView!!.height.toFloat() * 1.1f

        return handled
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        isAnimate = true
        return super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {

        println("cxb:onNestedPreScroll:type=${type}")
        if (targetView != null && (dy < 0 && child.bottom >= parentHeight || dy > 0 && child.bottom > parentHeight)) {
            if (type == 0) scale(child, target, dy)
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        println("cxb:onNestedPreFling")
        if (velocityY > 100) {
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        println("cxb:onStopNestedScroll:type=${type}")

//        if (type == ViewCompat.TYPE_TOUCH) recovery(abl)
        if (type == 0) recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun initial(abl: AppBarLayout) {
        abl.clipChildren = false
        parentHeight = abl.height
        targetViewHeight = targetView!!.height
    }

    private fun scale(abl: AppBarLayout, target: View, dy: Int) {
        println("cxb:scaleBefore:totalDy=${totalDy},dy=${dy}")
        totalDy += (-dy).toFloat()
        totalDy = min(totalDy, targetHeight)
        println("cxb:scaleAfter:totalDy=${totalDy},targetHeight=${targetHeight}")
        lastScale = max(1f, 1f + totalDy / targetHeight)

        targetView!!.scaleX = lastScale
        targetView!!.scaleY = lastScale
        lastBottom = parentHeight + (targetViewHeight / 2 * (lastScale - 1)).toInt()
        abl.bottom = lastBottom
//        tabLayout?.bottom = lastBottom
//        tabLayout?.top = tabLayout!!.bottom - tabLayoutHeight

        tabLayoutItem.setTopAndBottom(abl.bottom - parentHeight)
        userTopItem.setTopAndBottom(abl.bottom - parentHeight)

        println("cxb:scaleAfter:abl.top=${abl.top},abl.bottom=${abl.bottom}")
        target.scrollY = 0
    }

    /**
     * 图片缩放恢复
     * appBarLayout位置恢复
     */
    private fun recovery(abl: AppBarLayout) {
        println("cxb:recovery:isAnimate=${isAnimate},totalDy=${totalDy}")
        if (totalDy > 0) {
            totalDy = 0f
            if (isAnimate) {
                val lastScale = this.lastScale
                val lastBottom = this.lastBottom
                val parentHeight = this.parentHeight
                val anim = ValueAnimator.ofFloat(lastScale, 1f).setDuration(250)
                anim.interpolator = DecelerateInterpolator()

                anim.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    targetView!!.scaleX = value
                    targetView!!.scaleY = value
                    abl.bottom =
                        (lastBottom - (lastBottom - parentHeight) * animation.animatedFraction).toInt()
                    tabLayoutItem.setTopAndBottom(abl.bottom - parentHeight)
                    userTopItem.setTopAndBottom(abl.bottom - parentHeight)

                }

                anim.start()
            } else {
                targetView!!.scaleX = 1f
                targetView!!.scaleY = 1f
                abl.bottom = parentHeight
                tabLayoutItem.setTopAndBottom(abl.bottom - parentHeight)
                userTopItem.setTopAndBottom(abl.bottom - parentHeight)

            }
        }
    }


    /**
     * 1.touch与fling并存的问题修复
     * 2.反向fling的问题修复
     */

    companion object {
        private const val TAG = "overScroll"
    }

    class ScrollItems {
        var itemView: View? = null
        var itemHeight: Int = 0
        fun setTopAndBottom(offset: Int) {
            itemView?.let {
                it.translationY = offset.toFloat()
            }
        }
    }
}