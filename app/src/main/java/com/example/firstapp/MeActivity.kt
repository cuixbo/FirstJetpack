package com.example.firstapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.databinding.ActivityMeBinding
import com.example.firstapp.ui.main.AppBarStateChangeListener
import com.example.firstapp.ui.main.SectionsPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.leaf.library.StatusBarUtil

class MeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeBinding
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeBinding.inflate(layoutInflater)
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


    }

    private fun initListener() {
        binding.collapsingToolbar
        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.COLLAPSED) {
                    StatusBarUtil.setDarkMode(this@MeActivity)
                } else
                    StatusBarUtil.setLightMode(this@MeActivity)
            }

        })
    }

}