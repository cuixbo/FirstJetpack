package com.example.firstapp.ui.beauty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentBeautyHomeBinding


/**
 * A placeholder fragment containing a simple view.
 */
class BeautyHomeFragment : Fragment(),View.OnClickListener {

    private var _binding: FragmentBeautyHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeautyHomeBinding.inflate(inflater, container, false)
        _binding?.apply {
            ivImg.setOnClickListener {
                goToDetail(it)
            }
            btnGoToMe.setOnClickListener {
                goToMe(it)
            }
            btnGoToMore.setOnClickListener {
                goToMore(it)
            }
        }
        return _binding!!.root
    }

    fun goToDetail(view: View) {
        println("detail")
        findNavController().apply {
            navigate(R.id.action_home_to_detail)
        }
    }

    fun goToMe(view: View) {
        println("me")
        findNavController().apply {
            navigate(R.id.action_home_to_me)
        }
    }

    fun goToMore(view: View) {
        println("more")
        findNavController().apply {
            navigate(R.id.action_home_to_more)
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
        fun newInstance(sectionNumber: Int): BeautyHomeFragment {
            return BeautyHomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        @JvmStatic
        fun newInstance(sectionContent: String): BeautyHomeFragment {
            return BeautyHomeFragment().apply {
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

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
