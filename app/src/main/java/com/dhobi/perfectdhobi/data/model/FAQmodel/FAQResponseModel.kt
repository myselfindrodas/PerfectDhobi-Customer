package com.dhobi.perfectdhobi.data.model.FAQmodel


import com.google.gson.annotations.SerializedName

data class FAQResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data Available..!!
    @SerializedName("status")
    val status: Boolean // true
)