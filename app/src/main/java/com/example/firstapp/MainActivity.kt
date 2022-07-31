package com.example.firstapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.ui.main.SectionsPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.leaf.library.StatusBarUtil

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

        //这两句设为透明状态栏，5.0之前是不适配的，可以在这里加安卓版本判断条件
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

//        StatusBarUtil.setTransparentForWindow(this);
//        StatusBarUtil.setPaddingTop(this,binding.appBar)


//        StatusBarUtil.setTranslucentForImageView(this, 0, null);

        viewPager = binding.includeMain.viewPager
        bottomNavigationView = binding.includeMain.bnvNavigation
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.currentItem = 4
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