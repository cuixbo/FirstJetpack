package com.example.firstapp.ui.beauty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentBeautyDetailBinding
import com.example.firstapp.databinding.FragmentBeautyMeBinding
import com.example.firstapp.databinding.FragmentMeBinding


/**
 * A placeholder fragment containing a simple view.
 */
class BeautyDetailFragment : Fragment() {


    private var _binding: FragmentBeautyDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBeautyDetailBinding.inflate(inflater, container, false)
        _binding?.apply {
            ivImg.setOnClickListener {
                goToMe(it)
            }
        }
        return _binding!!.root
    }

    fun goToMe(view: View) {
        println("me")
        findNavController().apply {
            navigate(R.id.action_detail_to_me)
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
        fun newInstance(sectionNumber: Int): BeautyDetailFragment {
            return BeautyDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        @JvmStatic
        fun newInstance(sectionContent: String): BeautyDetailFragment {
            return BeautyDetailFragment().apply {
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
