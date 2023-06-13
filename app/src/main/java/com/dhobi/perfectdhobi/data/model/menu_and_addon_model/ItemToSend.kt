package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class ItemToSend(
    @SerializedName("id")
    val id: String, // 646c723e3db4eeb78dba7f0b
    @SerializedName("name")
    val name: String, // Regular Wear
    @SerializedName("qty")
    var qty: Int=0,
    @SerializedName("unitprice")
    var unitprice: Float = 0f,
    @SerializedName("parent")
    var parent: String = "",


    // null
)