package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.RatechartModel.RateChart
import com.dhobi.perfectdhobi.data.model.ratemodel.RateModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentRateChartBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.AddonRatechartAdapter
import com.dhobi.perfectdhobi.ui.adapter.RatechartAdapter
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class RateChartFragment : Fragment(),RatechartAdapter.OnItemClickListener{

    lateinit var fragmentRateChartBinding: FragmentRateChartBinding
    lateinit var mainActivity: MainActivity
    private var ratechartAdapter: RatechartAdapter? = null
    private var addonRatechartAdapter: AddonRatechartAdapter?=null
    private lateinit var viewModel: CommonViewModel

//    private val ratechartListModelArrayList: ArrayList<RateModel>  = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRateChartBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_rate_chart, container, false)
        val root = fragmentRateChartBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentRateChartBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }


        fragmentRateChartBinding.topnav.tvNavtitle.text = "Back"

//        for (i in 0..5) {
//
//            ratechartListModelArrayList.add(
//                RateModel(
//                    "Perfect Press (Regular Wear)",
//                    "₹ 7"
//                )
//            )
//
//        }


        ratechartAdapter = RatechartAdapter(mainActivity, this@RateChartFragment)
        fragmentRateChartBinding.rvRatelist.adapter = ratechartAdapter
        fragmentRateChartBinding.rvRatelist.layoutManager = GridLayoutManager(mainActivity, 1)


        addonRatechartAdapter = AddonRatechartAdapter(mainActivity)
        fragmentRateChartBinding.rvAddonlist.adapter = addonRatechartAdapter
        fragmentRateChartBinding.rvAddonlist.layoutManager = GridLayoutManager(mainActivity, 1)

//        ratechartAdapter?.updateData(ratechartListModelArrayList)
        ratechartlist()

    }



    private fun ratechartlist(){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.ratechartlist(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        ratechartAdapter?.updateData(itResponse.data.rateCharts)
                                        addonRatechartAdapter?.updateData(itResponse.data.addonList)

                                    } else {

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                val builder = android.app.AlertDialog.Builder(mainActivity)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()


                            }

                            Status.LOADING -> {
                                mainActivity.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }



    override fun onClick(
        position: Int,
        view: View,
        id: Int,
        s: String,
        mRatechartListModelArrayList: ArrayList<RateChart>
    ) {

    }

}