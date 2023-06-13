package com.dhobi.perfectdhobi.data.model.UpdateAddressModel.UpdateAddressResponseModel


import com.google.gson.annotations.SerializedName

data class UpdateAddressResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Address updated Successfully!
    @SerializedName("status")
    val status: Boolean // true
)