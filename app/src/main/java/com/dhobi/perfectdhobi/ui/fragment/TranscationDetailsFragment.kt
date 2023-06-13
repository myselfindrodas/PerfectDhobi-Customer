package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.databinding.FragmentTransactionsBinding
import com.dhobi.perfectdhobi.databinding.FragmentTranscationDetailsBinding
import com.dhobi.perfectdhobi.ui.MainActivity

class TranscationDetailsFragment : Fragment() {

    lateinit var fragmentTranscationDetailsBinding: FragmentTranscationDetailsBinding
    lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTranscationDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_transcation_details, container, false)
        val root = fragmentTranscationDetailsBinding.root
        mainActivity = activity as MainActivity


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTranscationDetailsBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }

        fragmentTranscationDetailsBinding.topnav.tvNavtitle.text = "Back"
    }

}