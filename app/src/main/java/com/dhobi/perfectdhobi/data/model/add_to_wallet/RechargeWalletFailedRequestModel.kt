package com.dhobi.perfectdhobi.data.model.add_to_wallet

import com.google.gson.annotations.SerializedName

data class RechargeWalletFailedRequestModel(
    @SerializedName("razorpayErrorOrderId")
    val razorpayErrorOrderId: String,
    @SerializedName("razorpayErrorPaymentId")
    val razorpayErrorPaymentId: String
)
