package com.example.firstapp.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentMainBinding
import com.example.firstapp.databinding.FragmentMeBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.leaf.library.StatusBarUtil
import kotlin.math.abs

/**
 * A placeholder fragment containing a simple view.
 */
class MeFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tlTabs: TabLayout
    private lateinit var vpContent: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            // setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            setContent(arguments?.getString(ARG_SECTION_CONTENT) ?: "hello")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root = binding.root
        tlTabs = binding.tlTabs
        vpContent = binding.vpContent
        tlTabs.setupWithViewPager(vpContent)

        val activity = requireActivity() as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
        }
//        binding.toolbar.title = "小黄盖666"

        val mePagerAdapter = MePagerAdapter(requireContext(), childFragmentManager)
        vpContent.adapter = mePagerAdapter

        println(resources.displayMetrics.toString())

        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            val titleHeight = dip2px(requireContext(), 48F + 24F)
            override fun onStateChanged(
                appBarLayout: AppBarLayout?,
                state: State?
            ) {
                if (state == State.COLLAPSED) {
                    StatusBarUtil.setDarkMode(requireActivity())
                } else {
                    StatusBarUtil.setLightMode(requireActivity())
                }
            }

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                super.onOffsetChanged(appBarLayout, verticalOffset)
                appBarLayout?.let {
                    if (abs(verticalOffset) > it.totalScrollRange - titleHeight) {
                        binding.toolbar.setBackgroundColor(Color.WHITE)
                        StatusBarUtil.setColor(requireActivity(), Color.WHITE)
                    } else {
                        binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
                        StatusBarUtil.setColor(requireActivity(), Color.TRANSPARENT)
                    }
                }
            }

        })




        pageViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_CONTENT = "section_content"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): MeFragment {
            return MeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        @JvmStatic
        fun newInstance(sectionContent: String): MeFragment {
            return MeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_CONTENT, sectionContent)
                }
            }
        }

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density;
            return (dpValue * scale + 0.5f).toInt();
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density;
            return (pxValue / scale + 0.5f).toInt();
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
