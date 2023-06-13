package com.dhobi.perfectdhobi.data.model.expriedotp.ExpriedOtpResponseModel


import com.google.gson.annotations.SerializedName

data class ExpriedOtpResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // OTP expired !
    @SerializedName("status")
    val status: Boolean // true
)