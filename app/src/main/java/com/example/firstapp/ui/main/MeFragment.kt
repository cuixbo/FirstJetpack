package com.example.firstapp.ui.main

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
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

            val userHeight = dpToPx(requireContext(), 105)
            val titleHeight = dpToPx(requireContext(), 48)
            val collapseHeight = dpToPx(requireContext(), 280)
            val statusBarHeight = getStatusBarHeight(requireContext())

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
                    // 处理收起时，toolbar标题栏的变色
                    if (abs(verticalOffset) > it.totalScrollRange - userHeight) {
                        binding.toolbar.setBackgroundColor(Color.WHITE)
                        StatusBarUtil.setColor(requireActivity(), Color.WHITE)
                        StatusBarUtil.setDarkMode(requireActivity())
                    } else {
                        binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
                        StatusBarUtil.setColor(requireActivity(), Color.TRANSPARENT)
                        StatusBarUtil.setLightMode(requireActivity())
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

        fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Float {
            val r = context.resources
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        }

        // 获取状态栏高度
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        // 获取导航栏高度
        fun getNavigationBarHeight(context: Context): Int {
            var result = 0
            val resources: Resources = context.resources
            val resourceId: Int =
                resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
