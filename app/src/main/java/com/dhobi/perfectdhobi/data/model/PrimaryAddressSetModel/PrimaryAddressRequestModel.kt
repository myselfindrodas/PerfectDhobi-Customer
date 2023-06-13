package com.dhobi.perfectdhobi.data.model.PrimaryAddressSetModel

import com.google.gson.annotations.SerializedName

data class PrimaryAddressRequestModel(
    @SerializedName("addressId")
    val addressId: String
)