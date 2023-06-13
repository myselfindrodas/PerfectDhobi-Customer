package com.dhobi.perfectdhobi.data.model.BannerModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banners")
    val banners: List<String>
)