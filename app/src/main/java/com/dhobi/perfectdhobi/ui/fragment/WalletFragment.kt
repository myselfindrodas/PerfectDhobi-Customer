package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletRequestModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.TempOrderSummeryRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentHomeBinding
import com.dhobi.perfectdhobi.databinding.FragmentWalletBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class WalletFragment : Fragment() {
    lateinit var fragmentWalletBinding: FragmentWalletBinding
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentWalletBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)
        val root = fragmentWalletBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        fragmentWalletBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        fragmentWalletBinding.topnav.tvNavtitle.text = "Wallet"


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentWalletBinding){
            btnProceed.setOnClickListener {

                if (etAmount.text.toString().trim().isEmpty()){
                    Toast.makeText(mainActivity, "Enter amount", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                rechargeWallet(etAmount.text.toString().trim())
            }
            walletBalance()
        }
    }


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

                                        fragmentWalletBinding.tvWalletAmt.text="₹ ${itResponse.data.wallet.amount.numberDecimal}"

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
                                val builder = android.app.AlertDialog.Builder(requireContext())
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
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

    private fun rechargeWallet(  amount:String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.rechargeWallet(
                devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                RechargeWalletRequestModel(amount)
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        val amount = itResponse.data.amount
                                        val razorpayOrderId = itResponse.data.razorpayOrderId

                                        mainActivity.startPayment(razorpayOrderId,amount.toString())

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