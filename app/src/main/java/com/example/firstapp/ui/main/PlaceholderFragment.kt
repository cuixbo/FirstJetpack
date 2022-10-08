package com.example.firstapp.ui.main

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentMainBinding
import com.example.firstapp.net.Repository
import com.example.firstapp.utils.ReflectUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File
import kotlin.system.measureTimeMillis

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val progress: StateFlow<Int> = MutableStateFlow<Int>(0)
    private val imageUrl = "https://b.zol-img.com.cn/desk/bizhi/image/1/5120x2880/1635925199470.jpg"
    private val zipUrl =
        "https://apktxdl.vivo.com.cn/appstore/developer/apk/20220610/2022061017190762gb4.apk"
    private var downloadJob: Job? = null

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

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

        })


        binding.ivImage.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
            if (downloadJob == null || !downloadJob!!.isActive) {
                downloadJob = downloadImage(imageUrl)
            }
        }
        showImageSample(imageUrl)
        return root
    }

    private fun showImageSample(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.ivImage)
    }

    private fun downloadImage(url: String) = lifecycleScope.launchWhenResumed {
        Repository.downloadImage(url)
            .cancellable()
            .flowOn(Dispatchers.IO)
            .onStart {
                binding.ivImage.alpha = 0.3f
                binding.donutProgress.progress = 0f
                binding.donutProgress.visibility = View.VISIBLE
            }
            .onCompletion {
                binding.ivImage.alpha = 1f
                binding.donutProgress.visibility = View.GONE
                // stateFlow好像不会停止协程
                downloadJob?.cancel()
            }
            .stateIn(this)
            .collect {
                binding.sectionLabel.text = it.toString()
                binding.donutProgress.progress = it.toFloat()
            }

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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        @JvmStatic
        fun newInstance(sectionContent: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_CONTENT, sectionContent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
