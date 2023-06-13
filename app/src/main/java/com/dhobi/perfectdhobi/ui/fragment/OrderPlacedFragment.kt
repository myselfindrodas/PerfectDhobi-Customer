package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.databinding.FragmentOrderPlacedBinding
import com.dhobi.perfectdhobi.ui.MainActivity

class OrderPlacedFragment : Fragment() {

    lateinit var fragmentOrderPlacedBinding: FragmentOrderPlacedBinding
    lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderPlacedBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_placed, container, false)
        val root = fragmentOrderPlacedBinding.root
        mainActivity = activity as MainActivity

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentOrderPlacedBinding.topnav.tvNavtitle.text = "Back"

        fragmentOrderPlacedBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentOrderPlacedBinding.btnHome.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_home)
        }
    }

}