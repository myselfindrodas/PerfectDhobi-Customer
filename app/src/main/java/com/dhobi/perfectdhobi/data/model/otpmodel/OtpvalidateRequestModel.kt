package com.dhobi.perfectdhobi.data.model.otpmodel

import com.google.gson.annotations.SerializedName

data class OtpvalidateRequestModel(

    @SerializedName("phone")
    val phone: String,
    @SerializedName("otp")
    val otp: String
)