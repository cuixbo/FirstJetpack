package com.example.firstapp.ui.main

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.firstapp.BeautyActivity
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
        getMemory()
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
                        binding.tvToolbarTitle.visibility = View.VISIBLE
                        binding.toolbar.setBackgroundColor(Color.WHITE)
                        StatusBarUtil.setColor(requireActivity(), Color.WHITE)
                        StatusBarUtil.setDarkMode(requireActivity())
                    } else {
                        binding.tvToolbarTitle.visibility = View.INVISIBLE
                        binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
                        StatusBarUtil.setColor(requireActivity(), Color.TRANSPARENT)
                        StatusBarUtil.setLightMode(requireActivity())
                    }
                }
            }

        })

        binding.ivUserBg.setOnClickListener {
            startActivity(Intent(requireContext(), BeautyActivity::class.java))
        }


        pageViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    fun getMemory() {
        val runtime = Runtime.getRuntime()
        val activityManager =
            requireContext().getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        //最大分配内存
        val memory = activityManager!!.memoryClass
        //最大分配内存获取方法2
        val maxMemory = (runtime.maxMemory() / (1024 * 1024)).toFloat()
        //当前分配的总内存
        val totalMemory = (runtime.totalMemory() / (1024 * 1024)).toFloat()
        //剩余内存
        val freeMemory = (runtime.freeMemory() / (1024 * 1024)).toFloat()

        println("memory: $memory")
        println("maxMemory: $maxMemory")
        println("totalMemory: $totalMemory")
        println("freeMemory: $freeMemory")

        val metrics: DisplayMetrics = resources.displayMetrics
        resources.configuration.densityDpi
        val wm: WindowManager? = getSystemService(requireContext(), WindowManager::class.java)
        val metrics2 = DisplayMetrics()

        wm!!.defaultDisplay.getRealMetrics(metrics2)
        wm?.let {
            wm.defaultDisplay.getRealMetrics(metrics2)
//            println("wm.currentWindowMetrics:${wm.currentWindowMetrics.toString()}")
//            println("wm.maximumWindowMetrics:${wm.maximumWindowMetrics.toString()}")


        }

        println("densityDpi:${metrics.densityDpi}")    // 440
        println("metrics:${metrics.toString()}") // DisplayMetrics{density=2.75, width=1080, height=2276, scaledDensity=2.75, xdpi=394.705, ydpi=394.307}
        println("metrics2:${metrics2.toString()}")// DisplayMetrics{density=2.75, width=1080, height=2400, scaledDensity=2.75, xdpi=394.705, ydpi=394.307}
        println("resources.configuration.densityDpi:${resources.configuration.densityDpi}")// esources.configuration.densityDpi:440
        //  1080x1080+2400x2400 开平方 = 2631.81   2631.8/6.67 =394.57  394.57 /160 = 2.46
        //  1080x1080+2276x2276 开平方 = 2519.24   2519.24/6.67=377.70  377.70 /160 = 2.36
        //  1080x1080+2728x2728 开平方 = 2934.8    2934.8/6.67=440 440 /160 = 2.75


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
