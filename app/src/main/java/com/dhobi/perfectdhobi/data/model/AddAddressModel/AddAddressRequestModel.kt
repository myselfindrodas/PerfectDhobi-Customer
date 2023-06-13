package com.dhobi.perfectdhobi.data.model.AddAddressModel

import com.google.gson.annotations.SerializedName

data class AddAddressRequestModel(
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("apartmentName")
    val apartmentName: String,
    @SerializedName("streetDetails")
    val streetDetails: String,
    @SerializedName("areaDetails")
    val areaDetails: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("pinCode")
    val pinCode: String,
    @SerializedName("addressNickName")
    val addressNickName: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)