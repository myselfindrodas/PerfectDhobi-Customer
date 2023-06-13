package com.dhobi.perfectdhobi.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.base.BaseActivity
import com.dhobi.perfectdhobi.databinding.ActivitySplashScreenBinding
import com.dhobi.perfectdhobi.utils.AnimUtils
import com.dhobi.perfectdhobi.utils.PrefManager
import com.dhobi.perfectdhobi.utils.Shared_Preferences

class SplashScreen : BaseActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    private var prefManager: PrefManager? = null


    override fun resourceLayout(): Int {
        return R.layout.activity_splash_screen
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashScreenBinding
    }

    override fun setFunction() {
        callNextScreen()

    }

    private fun callNextScreen() {
        prefManager = PrefManager(this)

        Handler(Looper.myLooper()!!).postDelayed({


            if (prefManager!!.isFirstTimeLaunch) {
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()
            } else {

                if (Shared_Preferences.getLoginStatus()==true) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    AnimUtils.FadeOutAnim(this)
                    finish()
                } else {

                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    AnimUtils.FadeOutAnim(this)
                    finish()

                }

            }

        }, 3000)
    }
}