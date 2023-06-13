package com.dhobi.perfectdhobi.data.model.EditAddressModel


import com.google.gson.annotations.SerializedName

data class EditAddressResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data available !
    @SerializedName("status")
    val status: Boolean // true
)