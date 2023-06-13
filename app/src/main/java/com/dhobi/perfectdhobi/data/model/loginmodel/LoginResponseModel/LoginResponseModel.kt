package com.dhobi.perfectdhobi.data.model.loginmodel.LoginResponseModel


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // OTP has been sent !
    @SerializedName("status")
    val status: Boolean // true
)