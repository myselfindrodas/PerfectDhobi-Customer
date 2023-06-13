package com.dhobi.perfectdhobi.ui.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.*
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentBulkOrderBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.OrderAddonAdapter
import com.dhobi.perfectdhobi.ui.adapter.OrderItemAdapter
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class BulkOrderFragment : Fragment(), OrderItemAdapter.OnItemClickListener,
    OrderAddonAdapter.OnItemClickListener {

    lateinit var fragmentBulkOrderBinding: FragmentBulkOrderBinding
    lateinit var mainActivity: MainActivity
    private var orderItemAdapter: OrderItemAdapter? = null
    private var orderAddonAdapter: OrderAddonAdapter? = null
    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentBulkOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bulk_order, container, false)
        val root = fragmentBulkOrderBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBulkOrderBinding.topnav.tvNavtitle.text = "Order"


//        fragmentBulkOrderBinding.hq.setQuantitizerListener(object: QuantitizerListener {
//            override fun onIncrease() {
////                Toast.makeText(mainActivity, "inc", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onDecrease() {
////                Toast.makeText(mainActivity, "dec", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onValueChanged(value: Int) {
//                Toast.makeText(mainActivity, "value changed to : $value", Toast.LENGTH_SHORT).show()
//            }
//        })

        orderItemAdapter = OrderItemAdapter(mainActivity, this@BulkOrderFragment)
        fragmentBulkOrderBinding.rvOrderItem.adapter = orderItemAdapter
        fragmentBulkOrderBinding.rvOrderItem.layoutManager = GridLayoutManager(mainActivity, 1)



        orderAddonAdapter = OrderAddonAdapter(mainActivity, this@BulkOrderFragment)
        fragmentBulkOrderBinding.rvAddonItem.adapter = orderAddonAdapter
        fragmentBulkOrderBinding.rvAddonItem.layoutManager = GridLayoutManager(mainActivity, 1)


        orderItemList()

        fragmentBulkOrderBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        fragmentBulkOrderBinding.btnProceed.setOnClickListener {


            val selectedItem = ArrayList<ItemToSend>()
            val selectedAddOn = ArrayList<AddOnToSend>()
            val orderList = orderItemAdapter?.getSelectedOrder()!!
            val addonList = orderAddonAdapter?.getSelectedAddOnList()!!
            for (i in 0 until orderList.size) {
                val childlist = orderList[i].children
                for (j in childlist.indices) {
                    if (childlist[j].isChecked) {
                        // val totalPrice = childlist[j].qty * (childlist[j].price.numberDecimal.toFloat())
                        selectedItem.add(
                            ItemToSend(
                                childlist[j].id,
                                childlist[j].name,
                                childlist[j].qty,
                                childlist[j].price.numberDecimal.toFloat(),
                                childlist[j].parent
                            )
                        )
                    }
                }
            }
            for (i in 0 until addonList.size) {
                if (addonList[i].isChecked) {
                    // val totalPrice = childlist[j].qty * (childlist[j].price.numberDecimal.toFloat())
                    selectedAddOn.add(
                        AddOnToSend(
                            addonList[i].id,
                            addonList[i].name,
                            if (addonList[i].item_quantity.isEmpty()) 0 else addonList[i].item_quantity.toInt(),
                            addonList[i].price.numberDecimal.toFloat(),
                            addonList[i].associatedCategory
                        )
                    )
                }
            }

            if (selectedItem.isEmpty()) {
                Toast.makeText(mainActivity, "Select item first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sendorderItemList(it, selectedItem, selectedAddOn)


        }

        with(fragmentBulkOrderBinding) {

//            llSubPress.visibility=View.GONE
//            llSubCombo.visibility=View.GONE
//            llSubDryClean.visibility=View.GONE
//            llSubAccessories.visibility=View.GONE
//            llSubFurnising.visibility=View.GONE
//            llSubPressAds.visibility=View.GONE
//            llSubDelivery.visibility=View.GONE
//            llSubSpray.visibility=View.GONE
//            llSubStainRemoval.visibility=View.GONE
            llAdsOnList.visibility = View.GONE
            llNotesList.visibility = View.GONE
            llOrderList.visibility = View.VISIBLE
            changeBackgroundColor(tvOrderItm, viewOrder)
            llOrderItem.setOnClickListener {
                llOrderList.visibility = View.VISIBLE
                llAdsOnList.visibility = View.GONE
                llNotesList.visibility = View.GONE
                changeBackgroundColor(tvOrderItm, viewOrder)

            }
            llAddOns.setOnClickListener {
                llOrderList.visibility = View.GONE
                llNotesList.visibility = View.GONE
                llAdsOnList.visibility = View.VISIBLE
                changeBackgroundColor(tvAdsOn, viewAdsOn)
            }
            llNotes.setOnClickListener {
                llOrderList.visibility = View.GONE
                llAdsOnList.visibility = View.GONE
                llNotesList.visibility = View.VISIBLE
                changeBackgroundColor(tvNote, viewNote)
            }

//            llPressAds.setOnClickListener {
//                if (llSubPressAds.isVisible){
//                    llSubPressAds.visibility=View.GONE
//                    ivDown.rotation = 90f
//                }else{
//                    llSubPressAds.visibility=View.VISIBLE1
//                    ivDown.rotation = 270f
//                }
//            }
//            llStainRemoval.setOnClickListener {
//                if (llSubStainRemoval.isVisible){
//                    llSubStainRemoval.visibility=View.GONE
//                    ivDown.rotation = 90f
//                }else{
//                    llSubStainRemoval.visibility=View.VISIBLE
//                    ivDown.rotation = 270f
//                }
//            }
//            llDelivery.setOnClickListener {
//                if (llSubDelivery.isVisible){
//                    llSubDelivery.visibility=View.GONE
//                    ivDown.rotation = 90f
//                }else{
//                    llSubDelivery.visibility=View.VISIBLE
//                    ivDown.rotation = 270f
//                }
//            }
//            llSpray.setOnClickListener {
//                if (llSubSpray.isVisible){
//                    llSubSpray.visibility=View.GONE
//                    ivDown.rotation = 90f
//                }else{
//                    llSubSpray.visibility=View.VISIBLE
//                    ivDown.rotation = 270f
//                }
//            }


//            llPress.setOnClickListener {
//                if (llSubPress.isVisible){
//                    llSubPress.visibility=View.GONE
//                    ivDown.rotation = 90f
//                }else{
//                    llSubPress.visibility=View.VISIBLE
//                    ivDown.rotation = 270f
//                }
//            }
//            llCombo.setOnClickListener {
//                if (llSubCombo.isVisible){
//                    llSubCombo.visibility=View.GONE
//                    ivDown1.rotation = 90f
//                }else{
//                    llSubCombo.visibility=View.VISIBLE
//                    ivDown1.rotation = 270f
//                }
//            }
//            llDryClean.setOnClickListener {
//                if (llSubDryClean.isVisible){
//                    llSubDryClean.visibility=View.GONE
//                    ivDown2.rotation = 90f
//                }else{
//                    llSubDryClean.visibility=View.VISIBLE
//                    ivDown2.rotation = 270f
//                }
//            }
//            llAccessories.setOnClickListener {
//                if (llSubAccessories.isVisible){
//                    llSubAccessories.visibility=View.GONE
//                    ivDown3.rotation = 90f
//                }else{
//                    llSubAccessories.visibility=View.VISIBLE
//                    ivDown3.rotation = 270f
//                }
//            }
//            llFurnising.setOnClickListener {
//                if (llSubFurnising.isVisible){
//                    llSubFurnising.visibility=View.GONE
//                    ivDown4.rotation = 90f
//                }else{
//                    llSubFurnising.visibility=View.VISIBLE
//                    ivDown4.rotation = 270f
//                }
//            }
        }


    }

    private fun changeBackgroundColor(text: TextView, view: View) {
        with(fragmentBulkOrderBinding) {

            tvOrderItm.setTextColor(ContextCompat.getColor(mainActivity, R.color.grey_text))
            viewOrder.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.grey_text))
            tvAdsOn.setTextColor(ContextCompat.getColor(mainActivity, R.color.grey_text))
            viewAdsOn.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.grey_text))
            tvNote.setTextColor(ContextCompat.getColor(mainActivity, R.color.grey_text))
            viewNote.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.grey_text))


            text.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
            view.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.blue))
        }
    }


    private fun orderItemList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.menuandaddon(
                devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob"
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        orderItemAdapter?.updateData(itResponse.data.menuList)
                                        orderAddonAdapter?.updateData(itResponse.data.addonList)

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

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

    private fun sendorderItemList(
        view: View,
        item: ArrayList<ItemToSend>,
        addOns: ArrayList<AddOnToSend>
    ) {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.createTempOrder(
                devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                MenuAddOnSendRequestModel(
                    addOns = addOns,
                    items = item,
                    note = fragmentBulkOrderBinding.tvNote.text.toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        val tempOrderId = itResponse.data.tempOrderId
                                        val bundle = Bundle()
                                        bundle.putString("tempOrderId", tempOrderId)
                                        val navController = Navigation.findNavController(view)
                                        navController.navigate(R.id.nav_ordersummery, bundle)
                                        //  orderItemAdapter?.updateData(itResponse.data.menuList)
                                        // orderAddonAdapter?.updateData(itResponse.data.addonList)

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

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

        /*var overallAmount = 0f
        var addOnAmount = 0f*/


    override fun onAddonClick(
        position: Int,
        view: View,
        mOrderaddonModelArrayList: ArrayList<Addon>
    ) {

       /* val addonList = orderAddonAdapter?.getSelectedAddOnList()!!
        addOnAmount = 0f
        for (i in 0 until addonList.size) {
            if (addonList[i].isChecked) {

                addOnAmount += addonList[i].pricetotal
               *//* fragmentBulkOrderBinding.txtTotalAmount.text =
                    "Amount : ₹ ${overallAmount + addOnAmount}"*//*
            }
        }*/

    }


    override fun onClick(
        position: Int,
        view: View,
        mOrderitemModelArrayList: ArrayList<Menu>
    ) {
        Log.d(TAG, "itemselectedarray-->$mOrderitemModelArrayList")


       /* val orderList = orderItemAdapter?.getSelectedOrder()!!
        overallAmount = 0f
        for (i in 0 until orderList.size) {
            val childlist = orderList[i].children
            for (j in childlist.indices) {
                if (childlist[j].isChecked) {
                    //val totalPrice = childlist[j].qty * (childlist[j].pricetotal)

                    overallAmount += childlist[j].pricetotal
                }
            }
        }*/

       // fragmentBulkOrderBinding.txtTotalAmount.text = "Amount : ₹ ${overallAmount + addOnAmount}"
    }

}