package com.dhobi.perfectdhobi.data.model.otpmodel.otpResponseModel


import com.google.gson.annotations.SerializedName

data class OtpResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // OTP Validated !
    @SerializedName("status")
    val status: Boolean // true
)