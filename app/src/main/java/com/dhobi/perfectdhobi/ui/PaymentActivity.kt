package com.dhobi.perfectdhobi.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        /*
       * To ensure faster loading of the Checkout form,
       * call this method as early as possible in your checkout flow
       * */
       // val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime

    }

    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        Checkout.preload(applicationContext)
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_QgKM8E1RJQWaGB")
        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email",Shared_Preferences.getEmail())
            prefill.put("contact",Shared_Preferences.getPhoneNo())

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {


    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

    }
}