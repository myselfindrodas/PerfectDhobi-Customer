package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class AddOnToSend(
    @SerializedName("id")
    val id: String, // 646c723e3db4eeb78dba7f0b
    @SerializedName("name")
    val name: String, // Regular Wear
    @SerializedName("qty")
    var qty: Int=0,
    @SerializedName("unitprice")
    var unitprice: Float = 0f,
    @SerializedName("associatedCategory")
    var associatedCategory: String = "",


    // null
)