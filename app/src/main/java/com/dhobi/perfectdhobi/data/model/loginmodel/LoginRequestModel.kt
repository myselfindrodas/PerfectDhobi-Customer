package com.dhobi.perfectdhobi.data.model.loginmodel

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("deviceToken")
    val deviceToken: String,
    @SerializedName("userType")
    val userType: String
)