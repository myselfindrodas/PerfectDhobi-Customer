package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.myordermodel.MyorderModel
import com.dhobi.perfectdhobi.data.model.review.ReviewModel
import com.dhobi.perfectdhobi.databinding.FragmentMyOrderBinding
import com.dhobi.perfectdhobi.databinding.FragmentReviewsBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.MyOrderAdapter
import com.dhobi.perfectdhobi.ui.adapter.ReviewAdapter

class MyOrderFragment : Fragment(), MyOrderAdapter.OnItemClickListener {
    lateinit var fragmentMyOrderBinding: FragmentMyOrderBinding
    lateinit var mainActivity: MainActivity
    private var myOrderAdapter:MyOrderAdapter?=null
    private val myorderModelArrayList: ArrayList<MyorderModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMyOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_order, container, false)
        val root = fragmentMyOrderBinding.root
        mainActivity = activity as MainActivity

        fragmentMyOrderBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }



        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMyOrderBinding.topnav.tvNavtitle.text = "Back"

        for (i in 0..5) {

            myorderModelArrayList.add(
                MyorderModel(
                    "Delivery Date : 20-04-2023",
                    "Order ID : #PD1234567890",
                    "Amount : ₹ 67.26"
                )
            )

        }



        myOrderAdapter = MyOrderAdapter(mainActivity, this@MyOrderFragment)
        fragmentMyOrderBinding.rvMyorderlist.adapter = myOrderAdapter
        fragmentMyOrderBinding.rvMyorderlist.layoutManager = GridLayoutManager(mainActivity, 1)

        myOrderAdapter?.updateData(myorderModelArrayList)


    }

    override fun onClick(position: Int, view: View, mOrderModelArrayList: ArrayList<MyorderModel>) {


    }

}