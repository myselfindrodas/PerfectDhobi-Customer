package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("$"+"numberDecimal")
    val numberDecimal: String // 49.00
)