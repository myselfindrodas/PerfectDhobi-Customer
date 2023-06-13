package com.dhobi.perfectdhobi.data.model.menu_and_addon_model

import com.google.gson.annotations.SerializedName

data class MenuAddOnSendRequestModel(

    @SerializedName("addOns")
    val addOns: ArrayList<AddOnToSend> = arrayListOf(),
    @SerializedName("items")
    val items: ArrayList<ItemToSend>,
    @SerializedName("note")
    val note: String,
    @SerializedName("coupon")
    val coupon: String="",
    @SerializedName("paymentMethod")
    val paymentMethod: String="",
)
