package com.dhobi.perfectdhobi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.databinding.ActivityInfoScreenBinding
import com.dhobi.perfectdhobi.databinding.ActivityOtpBinding

class InfoScreen : BaseActivity() {

    lateinit var binding: ActivityInfoScreenBinding

    override fun resourceLayout(): Int {
        return R.layout.activity_info_screen
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityInfoScreenBinding

    }

    override fun setFunction() {
        binding.btnClose.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}