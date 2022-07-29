package com.example.firstapp

import android.os.Bundle
import android.os.Handler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.firstapp.ui.main.SectionsPagerAdapter
import com.example.firstapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()


//        bottomNavigationView.itemIconSize = 0

//        bottomNavigationView.add


    }

    private fun initView() {
//        tabs = binding.tabs
        viewPager = binding.includeMain.viewPager
        bottomNavigationView = binding.includeMain.bnvNavigation
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
//        tabs.setupWithViewPager(viewPager)
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
    }

}