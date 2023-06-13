package com.dhobi.perfectdhobi.data.model.place_temp_order_model


import com.google.gson.annotations.SerializedName

data class PlaceOrderResponseModel(
    @SerializedName("data")
    val `data`: PlaceOrderData,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Order Successfully placed 
    @SerializedName("status")
    val status: Boolean // true
)