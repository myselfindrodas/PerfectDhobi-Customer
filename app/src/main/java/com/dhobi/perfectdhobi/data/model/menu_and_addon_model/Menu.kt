package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("children")
    var children: List<Children>,
    @SerializedName("_id")
    val id: String, // 646c6fda3db4eeb78dba7eef
    @SerializedName("name")
    val name: String, // Perfect Press
    @SerializedName("parent")
    val parent: String, // 0
    @SerializedName("price")
    val price: PriceXX,
    var isExpanded: Boolean=false // null

)