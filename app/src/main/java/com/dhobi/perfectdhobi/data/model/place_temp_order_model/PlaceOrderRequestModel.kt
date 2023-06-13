package com.dhobi.perfectdhobi.data.model.place_temp_order_model

import com.google.gson.annotations.SerializedName

data class PlaceOrderRequestModel(
    @SerializedName("orderAmount")
    val orderAmount: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("gst")
    val gst: String,
    @SerializedName("payableAmount")
    val payableAmount: String,
    @SerializedName("paymentMethod")
    val paymentMethod: String
)