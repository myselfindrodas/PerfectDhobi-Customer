package com.dhobi.perfectdhobi.data.model.BannerModel


import com.google.gson.annotations.SerializedName

data class BannerResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data available!
    @SerializedName("status")
    val status: Boolean // true
)