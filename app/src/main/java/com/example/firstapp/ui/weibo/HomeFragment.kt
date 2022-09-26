package com.example.firstapp.ui.weibo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.FragmentHomeBinding
import com.example.firstapp.net.FeedsListResponse
import com.example.firstapp.net.Repository
import com.example.firstapp.ui.main.PageViewModel
import com.leaf.library.StatusBarUtil
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val pageViewModel: PageViewModel by activityViewModels()
    private val feedData: MutableList<FeedsListResponse.Feed> = mutableListOf()
    private val adapter = FeedAdapter(feedData)


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setDarkMode(requireActivity())
        StatusBarUtil.setColor(requireActivity(), Color.parseColor("#f9f9f9"))
        getData()
        pageViewModel.refresh.observe(this) { t ->
            if (t) getData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

        }


        //   tlTabs.setupWithViewPager(vpContent)


        return binding.root
    }

    private fun getData() {
        pageViewModel.viewModelScope.launch {
            val time = measureTimeMillis {
                val feeds = Repository.getHotTimelineFeeds(0, 0)
                feedData.clear()
                feedData.addAll(feeds)
                adapter.notifyDataSetChanged()
            }
            println("cxb time $time")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
