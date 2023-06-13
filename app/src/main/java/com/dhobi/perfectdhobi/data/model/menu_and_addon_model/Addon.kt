package com.dhobi.perfectdhobi.data.model.menu_and_addon_model


import com.google.gson.annotations.SerializedName

data class Addon(
    @SerializedName("createdAt")
    val createdAt: String, // 2023-05-23T12:19:27.666Z
    @SerializedName("_id")
    val id: String, // 646caf4f5bec63d0da93bbec
    @SerializedName("isActive")
    val isActive: Boolean, // true
    @SerializedName("isDeleted")
    val isDeleted: Boolean, // false
    @SerializedName("name")
    val name: String, // Perfect Express Delivery
    @SerializedName("price")
    val price: Price,
    @SerializedName("quantity")
    var quantity: String, // disabled
    @SerializedName("updatedAt")
    val updatedAt: String, // 2023-05-23T12:19:27.666Z
    @SerializedName("__v")
    val v: Int, // 0
    @SerializedName("associatedCategory")
    var associatedCategory: String="", // null
    var isExpanded: Boolean=false, // null, // null
    var isChecked: Boolean=false, // null
    var pricetotal: Float = 0f,
    var item_quantity: String = "",


)