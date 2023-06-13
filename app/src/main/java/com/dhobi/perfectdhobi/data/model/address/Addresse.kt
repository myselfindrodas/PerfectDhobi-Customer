package com.dhobi.perfectdhobi.data.model.address


import com.google.gson.annotations.SerializedName

data class Addresse(
    @SerializedName("addressNickName")
    val addressNickName: String, // Home
    @SerializedName("apartmentName")
    val apartmentName: String, // Shanti neer
    @SerializedName("areaDetails")
    val areaDetails: String, // Dummy Area Details
    @SerializedName("city")
    val city: String, // Kolkata
    @SerializedName("houseNo")
    val houseNo: String, // 144/B
    @SerializedName("_id")
    val id: String, // 6454a009dc347b78c414cfda
    @SerializedName("isPrimary")
    var isPrimary: Boolean, // false
    @SerializedName("latitude")
    val latitude: Any?, // null
    @SerializedName("longitude")
    val longitude: Any?, // null
    @SerializedName("pinCode")
    val pinCode: Int, // 700154
    @SerializedName("streetDetails")
    val streetDetails: String // 1/B Dummy Street
)