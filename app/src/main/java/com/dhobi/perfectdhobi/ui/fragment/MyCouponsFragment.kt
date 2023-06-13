package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.mycouponmodel.CouponModel
import com.dhobi.perfectdhobi.databinding.FragmentMyCouponsBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.MyCouponAdapter

class MyCouponsFragment : Fragment(), MyCouponAdapter.OnItemClickListener {

    lateinit var fragmentMyCouponsBinding: FragmentMyCouponsBinding
    lateinit var mainActivity: MainActivity


    private var myCouponAdapter: MyCouponAdapter? = null
    private val mycouponModelArrayList: ArrayList<CouponModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMyCouponsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_coupons, container, false)
        val root = fragmentMyCouponsBinding.root
        mainActivity = activity as MainActivity


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentMyCouponsBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }

        fragmentMyCouponsBinding.topnav.tvNavtitle.text = "Back"



        for (i in 0..5) {

            mycouponModelArrayList.add(
                CouponModel(
                    "Get ₹ 135 Off on order of ₹ 1000",
                    "Valid Once per customer",
                    "PERFECT DHOBI 135"
                )
            )

        }


        myCouponAdapter = MyCouponAdapter(mainActivity, this@MyCouponsFragment)
        fragmentMyCouponsBinding.rvmyCoupons.adapter = myCouponAdapter
        fragmentMyCouponsBinding.rvmyCoupons.layoutManager = GridLayoutManager(mainActivity, 1)

        myCouponAdapter?.updateData(mycouponModelArrayList)


    }

    override fun onClick(
        position: Int,
        view: View,
        mMycouponModelArrayList: ArrayList<CouponModel>
    ) {


    }

}