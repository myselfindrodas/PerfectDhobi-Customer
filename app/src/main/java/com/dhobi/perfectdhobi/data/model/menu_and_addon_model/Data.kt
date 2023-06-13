package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("addonList")
    val addonList: List<Addon>,
    @SerializedName("menuList")
    val menuList: List<Menu>
)