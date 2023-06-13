package com.dhobi.perfectdhobi.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.loginmodel.LoginRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.ActivityLoginBinding
import com.dhobi.perfectdhobi.databinding.ActivityStartscreenBinding
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class Login : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLoginBinding

    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        binding.btnLogin.setOnClickListener {

            if (binding.etMobile.text.length==0){

                Toast.makeText(this, "Enter Mobile Number!", Toast.LENGTH_SHORT).show()
            }else {

                Loginapi(binding.etMobile.text.toString())
            }


        }

    }


    private fun Loginapi(mobile:String){


        if (Utilities.isNetworkAvailable(this)) {

            viewModel.login(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
            source = "mob", LoginRequestModel(phone = mobile, deviceToken = "wqwqeqeqeq", userType = "customer")).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status==true){
                                val builder = AlertDialog.Builder(this@Login)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    val intent = Intent(this, OTP::class.java)
                                    intent.putExtra("otp", resource.data.data.otp.toString())
                                    intent.putExtra("phone", mobile)
                                    startActivity(intent)
                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()
                            }else{

                                val builder = AlertDialog.Builder(this@Login)
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
                            Log.d(ContentValues.TAG, "print-->"+ resource.data?.status)
                            if (it.message!!.contains("401",true)) {
                                val builder = AlertDialog.Builder(this@Login)
                                builder.setMessage("Invalid Employee Id / Password")
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
                            }else
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }



        }else{

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