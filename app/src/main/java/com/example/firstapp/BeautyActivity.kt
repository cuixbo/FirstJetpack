package com.example.firstapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import com.example.firstapp.databinding.ActivityBeautyBinding
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.databinding.ActivityMeBinding
import com.example.firstapp.ui.main.AppBarStateChangeListener
import com.example.firstapp.ui.main.SectionsPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.leaf.library.StatusBarUtil

class BeautyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeautyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeautyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()
    }

    private fun initView() {
        val navHostFragment = binding.fragmentContainer.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController


    }

    private fun initListener() {

    }

}