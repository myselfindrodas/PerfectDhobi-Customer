package com.dhobi.perfectdhobi.ui.fragment

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.timelineitem.TimeLineModel
import com.dhobi.perfectdhobi.databinding.FragmentOrderDetailsBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.TimeLineBaseAdapter
import com.lriccardo.timelineview.TimelineDecorator

class OrderDetailsFragment : Fragment() {

    lateinit var fragmentOrderDetailsBinding: FragmentOrderDetailsBinding
    lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)
        val root = fragmentOrderDetailsBinding.root
        mainActivity = activity as MainActivity

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentOrderDetailsBinding.topnav.tvNavtitle.text = "Back"

        fragmentOrderDetailsBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        with(fragmentOrderDetailsBinding){

            llSubPress.visibility= View.GONE
            llSubCombo.visibility= View.GONE
            llSubDryClean.visibility= View.GONE
            llSubAccessories.visibility= View.GONE
            llSubFurnising.visibility= View.GONE
            llSubPressAds.visibility= android.view.View.GONE
            llSubDelivery.visibility= android.view.View.GONE
            llSubSpray.visibility= android.view.View.GONE
            llSubStainRemoval.visibility= android.view.View.GONE
            llAdsOnList.visibility= android.view.View.GONE
            llTracking.visibility= android.view.View.VISIBLE
            llOrderList.visibility= android.view.View.GONE
            changeBackgroundColor(tvOrderItm,viewOrder)
            llOrderItem.setOnClickListener {
                llOrderList.visibility= android.view.View.VISIBLE
                llAdsOnList.visibility= android.view.View.GONE
                llTracking.visibility= android.view.View.GONE
                changeBackgroundColor(tvOrderItm,viewOrder)

            }
            llAddOns.setOnClickListener {
                llOrderList.visibility= android.view.View.GONE
                llTracking.visibility= android.view.View.GONE
                llAdsOnList.visibility= android.view.View.VISIBLE
                changeBackgroundColor(tvAdsOn,viewAdsOn)
            }
            tvTrack.setOnClickListener {
                llOrderList.visibility= android.view.View.GONE
                llAdsOnList.visibility= android.view.View.GONE
                llTracking.visibility= android.view.View.VISIBLE
                changeBackgroundColor(tvTrack,viewTrack)
            }

            llPressAds.setOnClickListener {
                if (llSubPressAds.isVisible){
                    llSubPressAds.visibility= android.view.View.GONE
                    ivDown.rotation = 90f
                }else{
                    llSubPressAds.visibility= android.view.View.VISIBLE
                    ivDown.rotation = 270f
                }
            }
            llStainRemoval.setOnClickListener {
                if (llSubStainRemoval.isVisible){
                    llSubStainRemoval.visibility= android.view.View.GONE
                    ivDown.rotation = 90f
                }else{
                    llSubStainRemoval.visibility= android.view.View.VISIBLE
                    ivDown.rotation = 270f
                }
            }
            llDelivery.setOnClickListener {
                if (llSubDelivery.isVisible){
                    llSubDelivery.visibility= android.view.View.GONE
                    ivDown.rotation = 90f
                }else{
                    llSubDelivery.visibility= android.view.View.VISIBLE
                    ivDown.rotation = 270f
                }
            }
            llSpray.setOnClickListener {
                if (llSubSpray.isVisible){
                    llSubSpray.visibility= android.view.View.GONE
                    ivDown.rotation = 90f
                }else{
                    llSubSpray.visibility= android.view.View.VISIBLE
                    ivDown.rotation = 270f
                }
            }


            llPress.setOnClickListener {
                if (llSubPress.isVisible){
                    llSubPress.visibility= android.view.View.GONE
                    ivDown.rotation = 90f
                }else{
                    llSubPress.visibility= android.view.View.VISIBLE
                    ivDown.rotation = 270f
                }
            }
            llCombo.setOnClickListener {
                if (llSubCombo.isVisible){
                    llSubCombo.visibility= android.view.View.GONE
                    ivDown1.rotation = 90f
                }else{
                    llSubCombo.visibility= android.view.View.VISIBLE
                    ivDown1.rotation = 270f
                }
            }
            llDryClean.setOnClickListener {
                if (llSubDryClean.isVisible){
                    llSubDryClean.visibility= android.view.View.GONE
                    ivDown2.rotation = 90f
                }else{
                    llSubDryClean.visibility= android.view.View.VISIBLE
                    ivDown2.rotation = 270f
                }
            }
            llAccessories.setOnClickListener {
                if (llSubAccessories.isVisible){
                    llSubAccessories.visibility= android.view.View.GONE
                    ivDown3.rotation = 90f
                }else{
                    llSubAccessories.visibility= android.view.View.VISIBLE
                    ivDown3.rotation = 270f
                }
            }
            llFurnising.setOnClickListener {
                if (llSubFurnising.isVisible){
                    llSubFurnising.visibility= android.view.View.GONE
                    ivDown4.rotation = 90f
                }else{
                    llSubFurnising.visibility= android.view.View.VISIBLE
                    ivDown4.rotation = 270f
                }
            }
        }



        val date: ArrayList<String> = arrayListOf()
        date.add("24-04-2023")
        date.add("25-04-2023")
        date.add("26-04-2023")
        date.add("27-04-2023")

        getTimelineView(
            1,
            date,
            "28-04-2023",
            "11.00AM"
        )

    }



    private fun getTimelineView(
        position: Int,
        date: List<String>,
        expectedDate: String,
        expectedTime: String
    ) {

        with(fragmentOrderDetailsBinding) {

            timelineRv.let {

                val timelineModelArrayList: java.util.ArrayList<TimeLineModel> = ArrayList<TimeLineModel>()
                timelineModelArrayList.add(
                    TimeLineModel(
                        date[0],
                        "Your Order Has Been Created on $expectedDate"
                    )
                )
                timelineModelArrayList.add(
                    TimeLineModel(
                        date[1],
                        "Order Received In Laundry"
                    )
                )
                timelineModelArrayList.add(
                    TimeLineModel(
                        date[2],
                        "Laundry Under Process"
                    )
                )
                timelineModelArrayList.add(
                    TimeLineModel(
                        date[3],
                        "Our Delivery Person Is On The Way & Will Reach You Shortly To Deliver on $expectedTime"
                    )
                )

                it.layoutManager =
                    LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
                it.adapter = TimeLineBaseAdapter(mainActivity, timelineModelArrayList, position)

                val colorPrimary = TypedValue()
                val theme: Resources.Theme = mainActivity.theme
                theme.resolveAttribute(R.color.orange, colorPrimary, true)

                it.addItemDecoration(
                    TimelineDecorator(
                        indicatorSize = 28f,
                        lineWidth = 10f,
                        padding = 30f,
                        position = TimelineDecorator.Position.Left,
                        indicatorColor = Color.GREEN,
                        lineColor = Color.GREEN
                    )
                )
            }
        }
    }




    private fun changeBackgroundColor(text: TextView, view: View){
        with(fragmentOrderDetailsBinding){

            tvOrderItm.setTextColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))
            viewOrder.setBackgroundColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))
            tvAdsOn.setTextColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))
            viewAdsOn.setBackgroundColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))
            tvTrack.setTextColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))
            viewTrack.setBackgroundColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.grey_text))


            text.setTextColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.black))
            view.setBackgroundColor(androidx.core.content.ContextCompat.getColor(mainActivity, com.dhobi.perfectdhobi.R.color.blue))
        }
    }

}