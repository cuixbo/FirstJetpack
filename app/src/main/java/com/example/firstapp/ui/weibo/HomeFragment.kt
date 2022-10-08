package com.example.firstapp.ui.weibo

import android.annotation.SuppressLint
import android.gesture.Gesture
import android.gesture.GestureUtils
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.FragmentHomeBinding
import com.example.firstapp.net.bean.FeedsListResponse
import com.leaf.library.StatusBarUtil

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: WeiboHomeViewModel by activityViewModels()
    private val feedData: MutableList<FeedsListResponse.Feed> = mutableListOf()
    private val adapter = FeedAdapter(feedData)


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setDarkMode(requireActivity())
        StatusBarUtil.setColor(requireActivity(), Color.parseColor("#f9f9f9"))
        initListeners()
        getData()
    }

    private fun initListeners() {
        homeViewModel.refresh.observe(this) { t ->
            if (t) getData()
        }
        homeViewModel.feeds.observe(this) {
            if (!it.isNullOrEmpty()) {
                feedData.clear()
                feedData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        homeViewModel.stateFeeds.observe(this) {
            when (it) {
                is State.Loading -> println("cxb:loading")
                is State.Success -> println("cxb:Success")
                is State.Error -> println("cxb:Error")
            }
        }

        homeViewModel.feedsList.observe(this) {
            println("cxb: homeViewModel.feedsList.observe")
            if (it is State.Success) {
                feedData.clear()
                feedData.addAll(it.data)
                adapter.notifyDataSetChanged()
            }
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
        binding.tvTitle.setOnClickListener {
            homeViewModel.refreshTrigger.value = true
        }


        return binding.root
    }

//    private fun getData() = homeViewModel.viewModelScope.launch {
//        val time = measureTimeMillis {
//            val feeds = Repository.getHotTimelineFeeds(0, 0)
//            feedData.clear()
//            feedData.addAll(feeds)
//            adapter.notifyDataSetChanged()
//        }
//        println("cxb time $time")
//    }

    private fun getData() {
//        homeViewModel.getFeeds()
//        homeViewModel.getFeedsNew()
        homeViewModel.refreshTrigger.postValue(true)
        println("cxb getData")
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
