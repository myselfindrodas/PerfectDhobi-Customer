package com.dhobi.perfectdhobi.data.model.DeleteAddressModel

import com.google.gson.annotations.SerializedName

data class DeleteAddressRequestModel(
    @SerializedName("addressId")
    val addressId: String
)