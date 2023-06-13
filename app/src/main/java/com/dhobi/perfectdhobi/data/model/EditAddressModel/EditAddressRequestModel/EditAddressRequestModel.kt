package com.dhobi.perfectdhobi.data.model.EditAddressModel.EditAddressRequestModel

import com.google.gson.annotations.SerializedName

data class EditAddressRequestModel(
    @SerializedName("addressId")
    val addressId: String
)