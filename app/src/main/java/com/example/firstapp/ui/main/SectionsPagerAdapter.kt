package com.example.firstapp.ui.main

import android.content.Context
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.firstapp.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4,
    R.string.tab_text_5,
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        // Show 5 total pages.
        return 5
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return when (position) {
            4 -> MeFragment.newInstance(getPageTitle(position));
            else -> PlaceholderFragment.newInstance(getPageTitle(position))
        }
    }

    override fun getPageTitle(position: Int): String {
        return context.resources.getString(TAB_TITLES[position])
    }


}