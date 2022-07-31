package com.example.firstapp.ui.main

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        appBarLayout?.let {
            mCurrentState = if (verticalOffset == 0) {
                if (mCurrentState != State.EXPANDED) onStateChanged(it, State.EXPANDED);
                State.EXPANDED;
            } else if (abs(verticalOffset) >= it.totalScrollRange) {
                if (mCurrentState != State.COLLAPSED) onStateChanged(it, State.COLLAPSED);
                State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) onStateChanged(it, State.IDLE);
                State.IDLE;
            }
        }

    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)

}