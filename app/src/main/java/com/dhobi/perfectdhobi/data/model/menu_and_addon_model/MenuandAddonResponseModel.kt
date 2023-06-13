package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class MenuandAddonResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data Found!
    @SerializedName("status")
    val status: Boolean // true
)