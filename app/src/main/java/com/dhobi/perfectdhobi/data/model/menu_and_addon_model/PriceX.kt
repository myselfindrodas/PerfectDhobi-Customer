package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class PriceX(
    @SerializedName("$"+"numberDecimal")
    val numberDecimal: String // 7.00
)