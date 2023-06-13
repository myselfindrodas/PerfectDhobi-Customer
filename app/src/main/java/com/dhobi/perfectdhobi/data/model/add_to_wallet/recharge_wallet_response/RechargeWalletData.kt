package com.dhobi.perfectdhobi.data.model.add_to_wallet.recharge_wallet_response


import com.google.gson.annotations.SerializedName

data class RechargeWalletData(
    @SerializedName("amount")
    val amount: Int, // 10000
    @SerializedName("razorpayOrderId")
    val razorpayOrderId: String // order_LuFHPFzWSkggwZ
)