package com.dhobi.perfectdhobi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.databinding.ActivityStartscreenBinding
import com.dhobi.perfectdhobi.databinding.ActivityWelcomeBinding

class Startscreen : BaseActivity() {

    lateinit var binding: ActivityStartscreenBinding


    override fun resourceLayout(): Int {
        return R.layout.activity_startscreen
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityStartscreenBinding
    }

    override fun setFunction() {
        binding.btnContinue.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
    }


}