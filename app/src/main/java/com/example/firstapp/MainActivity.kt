package com.example.firstapp

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.ui.main.PageViewModel
import com.example.firstapp.ui.main.SectionsPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.leaf.library.StatusBarUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private val pageViewModel: PageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()
    }

    private fun initView() {
        StatusBarUtil.setTransparentForWindow(this);
        viewPager = binding.includeMain.viewPager
        bottomNavigationView = binding.includeMain.bnvNavigation
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.currentItem = 4
        bottomNavigationView.selectedItemId = R.id.menu_me
    }

    private fun initListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        bottomNavigationView.setOnItemSelectedListener {
            viewPager.currentItem = when (it.itemId) {
                R.id.menu_main -> 0
                R.id.menu_activity -> 1
                R.id.menu_center -> 2
                R.id.menu_message -> 3
                R.id.menu_me -> 4
                else -> 0
            }
            true
        }
        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    pageViewModel.setRefresh(true)
                }
            }
        }
    }

}