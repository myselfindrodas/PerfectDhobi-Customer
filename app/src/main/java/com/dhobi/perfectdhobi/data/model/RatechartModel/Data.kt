package com.dhobi.perfectdhobi.data.model.RatechartModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("addonList")
    val addonList: List<Addon>,
    @SerializedName("rateCharts")
    val rateCharts: List<RateChart>
)