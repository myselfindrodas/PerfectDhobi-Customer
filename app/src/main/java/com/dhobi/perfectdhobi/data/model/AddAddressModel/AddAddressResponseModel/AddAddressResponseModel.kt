package com.dhobi.perfectdhobi.data.model.AddAddressModel.AddAddressResponseModel


import com.google.gson.annotations.SerializedName

data class AddAddressResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Address Added Successfully!
    @SerializedName("status")
    val status: Boolean // true
)