package com.dhobi.perfectdhobi.data.model.add_to_wallet

import com.google.gson.annotations.SerializedName

data class RechargeWalletVerifiedRequestModel(
    @SerializedName("razorpayOrderId")
    val razorpayOrderId: String,
    @SerializedName("razorpayPaymentId")
    val razorpayPaymentId: String,
    @SerializedName("razorpaySignature")
    val razorpaySignature: String
)
