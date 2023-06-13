package com.dhobi.perfectdhobi.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.expriedotp.ExpriedOtpRequestModel
import com.dhobi.perfectdhobi.data.model.otpmodel.OtpvalidateRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.ActivityOtpBinding
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import java.util.*

class OTP : BaseActivity() {
    lateinit var binding: ActivityOtpBinding
    private lateinit var viewModel: CommonViewModel
    var countDownTimer: CountDownTimer? = null
    var validotp: String? = ""
    var phone: String? = ""
    var otp: String? = ""


    override fun resourceLayout(): Int {
        return R.layout.activity_otp
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityOtpBinding

    }

    override fun setFunction() {
        val intent = intent
        validotp = intent.getStringExtra("otp")
        phone = intent.getStringExtra("phone")

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm



        otpTimer(binding, this)
        with(binding) {

            val index0 = 0
            val index1 = 1
            val index2 = 2
            val index3 = 3
            val otp1st: String = validotp?.get(index0).toString()
            val otp2nd: String = validotp?.get(index1).toString()
            val otp3rd: String = validotp?.get(index2).toString()
            val otp4th: String = validotp?.get(index3).toString()

            otp1.setText(otp1st)
            otp2.setText(otp2nd)
            otp3.setText(otp3rd)
            otp4.setText(otp4th)

            tvMobilenumber.text = "mobile number " + phone


            val otptext = ArrayList<EditText>()
            otptext.add(otp1)
            otptext.add(otp2)
            otptext.add(otp3)
            otptext.add(otp4)
            setOtpEditTextHandler(otptext)
//            clDeliveryOtp.visibility=View.VISIBLE
//            clDeliverySuccess.visibility=View.GONE
            btnverify.setOnClickListener {
                otp = otp1.text.toString() +
                        otp2.text.toString() +
                        otp3.text.toString() +
                        otp4.text.toString()

                if (otp!!.length > 3) {

                    verifyOtp()
                } else {
                    Toast.makeText(this@OTP, "Enter valid OTP", Toast.LENGTH_SHORT).show()
                }
            }


            tvResentOTP.setOnClickListener {
                binding.otp1.setText("")
                binding.otp2.setText("")
                binding.otp3.setText("")
                binding.otp4.setText("")
                resendotp()
                otpTimer(binding, this@OTP)
            }


        }
    }


    private fun otpTimer(binding: ActivityOtpBinding, context: Context) {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        countDownTimer = object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.tvExpire.text = "00:" + String.format(
                    Locale.ENGLISH, "%02d", millisUntilFinished / 1000
                )
                binding.tvResentOTP.isEnabled = false
                binding.tvResentOTP.isClickable = false
                binding.tvResentOTP.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.grey_light,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.grey_light
                        )
                    }
                )
            }

            override fun onFinish() {
                expireotp()
                binding.tvExpire.text = "00:00"
                binding.tvResentOTP.isEnabled = true
                binding.tvResentOTP.isClickable = true
                binding.tvResentOTP.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.teal_700,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.teal_700
                        )
                    }
                )

            }
        }.start()
    }


    private fun setOtpEditTextHandler(otpEt: ArrayList<EditText>) { //This is the function to be called
        for (i in 0..3) { //Its designed for 6 digit OTP
            otpEt.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (i == 3 && otpEt[i].text.toString().isNotEmpty()) {

                    } else if (otpEt[i].text.toString().isNotEmpty()) {
                        otpEt[i + 1]
                            .requestFocus()
                    }
                }
            })
            otpEt[i].setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    otpEt[i].text.toString().isEmpty() && i != 0
                ) {
                    otpEt[i - 1].setText("")
                    otpEt[i - 1].requestFocus()
                }
                false
            })
        }
    }


    private fun verifyOtp() {


        if (Utilities.isNetworkAvailable(this)) {


            viewModel.validateotp(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                OtpvalidateRequestModel(phone = phone.toString(), otp = otp.toString())
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {


                                Shared_Preferences.setUserToken(resource.data.data.accessToken)
                                Shared_Preferences.setLoginStatus(true)
                                Toast.makeText(this, resource.data.message, Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@OTP, InfoScreen::class.java)
                                startActivity(intent)
                                finish()

                            } else {

                                val builder = AlertDialog.Builder(this@OTP)
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


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    private fun expireotp(){

        if (Utilities.isNetworkAvailable(this)) {


            viewModel.expireotp(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                ExpriedOtpRequestModel(phone = phone.toString())
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {


                                val builder = AlertDialog.Builder(this@OTP)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->


                                    binding.otp1.setText("")
                                    binding.otp2.setText("")
                                    binding.otp3.setText("")
                                    binding.otp4.setText("")
                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()


                            } else {

                                val builder = AlertDialog.Builder(this@OTP)
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


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    private fun resendotp(){

        if (Utilities.isNetworkAvailable(this)) {



            viewModel.resendotp(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob",
                ExpriedOtpRequestModel(phone = phone.toString())
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {


                                val builder = AlertDialog.Builder(this@OTP)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->


                                    var resentotp = resource.data.data.otp.toString()
                                    val index0 = 0
                                    val index1 = 1
                                    val index2 = 2
                                    val index3 = 3
                                    val resendotp1st: String = resentotp.get(index0).toString()
                                    val resendotp2nd: String = resentotp.get(index1).toString()
                                    val resendotp3rd: String = resentotp.get(index2).toString()
                                    val resendotp4th: String = resentotp.get(index3).toString()
                                    binding.otp1.setText(resendotp1st)
                                    binding.otp2.setText(resendotp2nd)
                                    binding.otp3.setText(resendotp3rd)
                                    binding.otp4.setText(resendotp4th)
                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()


                            } else {

                                val builder = AlertDialog.Builder(this@OTP)
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


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}