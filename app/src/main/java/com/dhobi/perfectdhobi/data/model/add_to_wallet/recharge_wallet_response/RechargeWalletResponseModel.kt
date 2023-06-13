package com.dhobi.perfectdhobi.data.model.add_to_wallet.recharge_wallet_response


import com.google.gson.annotations.SerializedName

data class RechargeWalletResponseModel(
    @SerializedName("data")
    val `data`: RechargeWalletData,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Intent created successfully in Razorpay Server
    @SerializedName("status")
    val status: Boolean // true
)