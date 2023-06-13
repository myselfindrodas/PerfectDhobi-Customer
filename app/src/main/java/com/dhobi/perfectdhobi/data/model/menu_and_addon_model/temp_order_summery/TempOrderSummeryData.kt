package com.dhobi.perfectdhobi.data.model.menu_and_addon_model.temp_order_summery


import com.google.gson.annotations.SerializedName

data class TempOrderSummeryData(
    @SerializedName("addOnTotal")
    val addOnTotal: String, // 99.00
    @SerializedName("discount")
    val discount: String, // 20.00
    @SerializedName("gst")
    val gst: String, // 169.20
    @SerializedName("itemTotal")
    val itemTotal: String, // 861.00
    @SerializedName("orderAmount")
    val orderAmount: String, // 960.00
    @SerializedName("payableAmount")
    val payableAmount: String // 1109.20
)