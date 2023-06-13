package com.dhobi.perfectdhobi.data.model.RatechartModel


import com.google.gson.annotations.SerializedName

data class PriceX(
    @SerializedName("$"+"numberDecimal")
    val numberDecimal: String // 25.00
)