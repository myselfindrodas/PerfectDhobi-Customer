package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Children(
    @SerializedName("children")
    val children: List<Any>,
    @SerializedName("_id")
    val id: String, // 646c723e3db4eeb78dba7f0b
    @SerializedName("name")
    val name: String, // Regular Wear
    @SerializedName("parent")
    val parent: String, // 646c6fda3db4eeb78dba7eef
    @SerializedName("price")
    val price: PriceX,
    var isChecked: Boolean=false, // null
    var qty: Int=0,
    var pricetotal: Float = 0f,
    var objList: JSONObject = JSONObject()


    // null
)