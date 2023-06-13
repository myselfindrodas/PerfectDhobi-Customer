package com.dhobi.perfectdhobi.data.model.RatechartModel


import com.google.gson.annotations.SerializedName

data class RateChart(
    @SerializedName("name")
    val name: String, // Perfect Press (Household Care Press)
    @SerializedName("price")
    val price: PriceX
)