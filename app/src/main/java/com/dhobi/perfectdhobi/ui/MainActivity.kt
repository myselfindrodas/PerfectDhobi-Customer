package com.dhobi.perfectdhobi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletFailedRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletVerifiedRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.ActivityMainBinding
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener,
    PaymentResultWithDataListener, ExternalWalletListener {
    lateinit var binding: ActivityMainBinding
    var mNavController: NavController? = null
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_main
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityMainBinding

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

    }

    override fun setFunction() {

        mNavController = findNavController(R.id.flFragment)

        mNavController?.addOnDestinationChangedListener(this)
        mNavController?.navigate(R.id.nav_home)

        Checkout.preload(this)
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
        hideKeyboard()

    }


    override fun onDestroy() {
        findNavController(R.id.flFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE

    }

    var orderid = ""
    var refid = ""

    fun startPayment(orderId: String, amount: String) {
        val co = Checkout()
        co.setKeyID("rzp_test_QgKM8E1RJQWaGB")
        // co.setKeyID("rzp_live_8Tu7NHdgOVlTDY")

        val totalamount: Double = amount.toDouble() * 100
        orderid = orderId


        try {
            val options = JSONObject()
            options.put("name", "Perfect Dhobi Payment")
            options.put("description", "Order Charges")
            options.put("image", R.drawable.logo)
            options.put("theme.color", R.color.blue)
            options.put("currency", "INR")
            options.put("order_id", orderId)
            options.put("amount", totalamount.toString())
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            co.open(this, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {


        p1?.let { data ->
            // try {

            //  unregisterReceiver(AutoReadOtpHelper(this))
            rechargeWalletVerify(data.orderId, data.paymentId, data.signature)
            /* }catch (e:Exception){
                 e.printStackTrace()
             }*/
        }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

        p2?.let { data ->

            rechargeWalletFailed(data.orderId, data.paymentId)
        }
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {

        p1?.let { data ->

            rechargeWalletVerify(data.orderId, data.paymentId, data.signature)
        }

    }


    private fun rechargeWalletVerify(
        razorpayOrderId: String, razorpayPaymentId: String, razorpaySignature: String
    ) {

        if (Utilities.isNetworkAvailable(this)) {
            viewModel.rechargeWalletVerify(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                RechargeWalletVerifiedRequestModel(
                    razorpayOrderId, razorpayPaymentId, razorpaySignature
                )
            ).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        Thread.sleep(800)
                                        val navController =
                                            Navigation.findNavController(this, R.id.flFragment)
                                        navController.popBackStack()
                                        navController.navigate(R.id.nav_wallet)


                                    } else {

                                        Toast.makeText(
                                            this, resource.data?.message, Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                hideProgressDialog()
                                val builder = android.app.AlertDialog.Builder(this)
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
                                showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }


    private fun rechargeWalletFailed(
        razorpayErrorOrderId: String, razorpayErrorPaymentId: String
    ) {

        if (Utilities.isNetworkAvailable(this)) {
            viewModel.rechargeWalletFailed(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                RechargeWalletFailedRequestModel(razorpayErrorOrderId, razorpayErrorPaymentId)
            ).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {


                                    } else {

                                        Toast.makeText(
                                            this, resource.data?.message, Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                hideProgressDialog()
                                val builder = android.app.AlertDialog.Builder(this)
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
                                showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }
}