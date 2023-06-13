package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.TempOrderSummeryRequestModel
import com.dhobi.perfectdhobi.data.model.place_temp_order_model.PlaceOrderRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentOrderSummeryBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class OrderSummeryFragment : Fragment() {

    lateinit var fragmentOrderSummeryBinding: FragmentOrderSummeryBinding
    lateinit var mainActivity: MainActivity
    lateinit var tempOrderId: String
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentOrderSummeryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_summery, container, false)
        val root = fragmentOrderSummeryBinding.root
        mainActivity = activity as MainActivity
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        if (requireArguments().containsKey("tempOrderId")) {
            tempOrderId = requireArguments().getString("tempOrderId", "")
        } else {
            Toast.makeText(mainActivity, "No Temporary order has been created", Toast.LENGTH_SHORT)
                .show()

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentOrderSummeryBinding.topnav.tvNavtitle.text = "Back"

        fragmentOrderSummeryBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentOrderSummeryBinding.btnconfirm.setOnClickListener { itview ->
            if (Shared_Preferences.getselectedAddress()!!.isNotEmpty()) {

                placeOrderSummery(itview,"wallet")
            } else {
                val builder = android.app.AlertDialog.Builder(mainActivity)
                builder.setMessage("Primary Address not set!! Add Primary Address?")
                builder.setPositiveButton(
                    "Ok"
                ) { dialog, which ->
                    val navController = Navigation.findNavController(itview)
                    navController.navigate(R.id.nav_add_address)
                    dialog.dismiss()
                }

                builder.setNegativeButton(
                    "No"
                ) { dialog, which ->
                    dialog.dismiss()


                }

                val alert = builder.create()
                alert.setOnShowListener { arg0 ->
                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(resources.getColor(R.color.blue))
                    alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(resources.getColor(R.color.blue))
                }
                alert.show()
            }


        }
        fragmentOrderSummeryBinding.btnWritereview.setOnClickListener {
            if (fragmentOrderSummeryBinding.etCode.text.toString().trim().isEmpty()) {
                Toast.makeText(mainActivity, "Apply coupon code first", Toast.LENGTH_SHORT).show()
                fragmentOrderSummeryBinding.etCode.requestFocus()
                return@setOnClickListener
            }
            getTemporderSummery(
                fragmentOrderSummeryBinding.root,
                tempOrderId,
                fragmentOrderSummeryBinding.etCode.text.toString().trim()
            )
        }

        fragmentOrderSummeryBinding.tvCouponCanceled.setOnClickListener {
            if (fragmentOrderSummeryBinding.etCode.text.toString().trim().isEmpty()) {
                Toast.makeText(mainActivity, "Apply coupon code first", Toast.LENGTH_SHORT).show()
                fragmentOrderSummeryBinding.etCode.requestFocus()
                return@setOnClickListener
            }
            fragmentOrderSummeryBinding.etCode.setText("")
            getTemporderSummery(fragmentOrderSummeryBinding.root, tempOrderId)

        }
        getTemporderSummery(fragmentOrderSummeryBinding.root, tempOrderId)

        walletBalance()

    }
    var price=""
    var discount = ""
    var gst=""
    var total_price =""
    private fun getTemporderSummery(view: View, tempOrderId: String, coupon: String = "") {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.tempOrderSummery(
                devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                TempOrderSummeryRequestModel(tempOrderId, coupon)
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        with(fragmentOrderSummeryBinding) {
                                            tvPrice.text = "₹ " + itResponse.data.orderAmount
                                            tvDiscount.text = "- ₹ " + itResponse.data.discount
                                            tvGst.text = "+ ₹ " + itResponse.data.gst
                                            tvTotalPrice.text = "₹ " + itResponse.data.payableAmount
                                            price = itResponse.data.orderAmount
                                            discount = itResponse.data.discount
                                            gst = itResponse.data.gst
                                            total_price = itResponse.data.payableAmount
                                            if (coupon.isEmpty()) {
                                                tvCouponApplied.visibility = View.GONE
                                                ivCouponApplied.visibility = View.GONE
                                                tvCouponCanceled.visibility = View.GONE
                                                tvCouponDetails.visibility = View.GONE
                                            } else {

                                                tvCouponApplied.visibility = View.VISIBLE
                                                ivCouponApplied.visibility = View.VISIBLE
                                                tvCouponCanceled.visibility = View.VISIBLE
                                                tvCouponDetails.visibility = View.VISIBLE
                                                tvCouponDetails.text =
                                                    "You have saved ₹${itResponse.data.discount} with this coupon"

                                            }
                                        }

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

    var walletBallence = ""
    private fun walletBalance() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.walletBalance(
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

                                        walletBallence=itResponse.data.wallet.amount.numberDecimal

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

    private fun placeOrderSummery(view: View, paymentType: String) {

        with(fragmentOrderSummeryBinding) {

            if (Utilities.isNetworkAvailable(mainActivity)) {
                viewModel.placeTempOrder(
                    devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                    source = "mob",
                    PlaceOrderRequestModel(
                        price.trim(),
                        discount.trim(),
                        gst.trim(),
                        total_price.trim(),
                        paymentType
                    )
                )
                    .observe(mainActivity) {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    mainActivity.hideProgressDialog()
                                    resource.data?.let { itResponse ->
                                        Toast.makeText(mainActivity, itResponse.message, Toast.LENGTH_SHORT)
                                            .show()

                                        if (itResponse.status) {



                                            val navController = Navigation.findNavController(view)
                                            navController.navigate(R.id.nav_orderplaced)
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
    }


}