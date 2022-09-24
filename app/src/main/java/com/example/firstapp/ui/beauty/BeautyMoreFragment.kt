package com.example.firstapp.ui.beauty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.PopUpToBuilder
import androidx.navigation.fragment.findNavController
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentBeautyMeBinding
import com.example.firstapp.databinding.FragmentBeautyMoreBinding
import com.example.firstapp.databinding.FragmentMeBinding


/**
 * A placeholder fragment containing a simple view.
 */
class BeautyMoreFragment : Fragment() {


    private var _binding: FragmentBeautyMoreBinding? = null

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

        _binding = FragmentBeautyMoreBinding.inflate(inflater, container, false)
        _binding?.apply {
            ivImg.setOnClickListener {
                goToDetail(it)
            }
            btnBackToDetail.setOnClickListener {
                backToDetail(it)
            }
        }
        return _binding!!.root
    }

    fun goToDetail(view: View) {
        println("detail")
        findNavController().apply {
            navigate(R.id.action_more_to_detail)
        }
    }

    fun backToDetail(view: View) {
        println("detail")
        findNavController().apply {
            navigate(
                R.id.action_more_to_detail,
                null,
                NavOptions.Builder().setPopUpTo(R.id.fragment_detail, true, false).build()
            )
//            parentFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
        fun newInstance(sectionNumber: Int): BeautyMoreFragment {
            return BeautyMoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        @JvmStatic
        fun newInstance(sectionContent: String): BeautyMoreFragment {
            return BeautyMoreFragment().apply {
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
