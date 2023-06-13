package com.dhobi.perfectdhobi.data.model.RatechartModel


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("$"+"numberDecimal")
    val numberDecimal: String // 49.00
)