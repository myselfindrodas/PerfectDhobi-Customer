package com.dhobi.perfectdhobi.data.model.menu_and_addon_model

import com.google.gson.annotations.SerializedName

data class TempOrderSummeryRequestModel(

    @SerializedName("tempOrderId")
    val tempOrderId: String,
    @SerializedName("coupon")
    val coupon: String=""
)
