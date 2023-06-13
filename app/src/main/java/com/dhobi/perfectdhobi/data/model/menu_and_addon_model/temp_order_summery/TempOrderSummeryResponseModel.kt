package com.dhobi.perfectdhobi.data.model.menu_and_addon_model.temp_order_summery


import com.google.gson.annotations.SerializedName

data class TempOrderSummeryResponseModel(
    @SerializedName("data")
    val `data`: TempOrderSummeryData,
    @SerializedName("errors")
    val errors: String?, // null
    @SerializedName("message")
    val message: String, // Coupon code applied Successfully
    @SerializedName("status")
    val status: Boolean // true
)