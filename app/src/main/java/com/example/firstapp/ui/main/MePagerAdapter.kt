package com.example.firstapp.ui.main

import android.content.Context
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.firstapp.R

private val TAB_TITLES = arrayOf(
    R.string.tab_me_1,
    R.string.tab_me_2,
    R.string.tab_me_3,
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class MePagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(getPageTitle(position))
    }

    override fun getPageTitle(position: Int): String {
        return context.resources.getString(TAB_TITLES[position])
    }
}