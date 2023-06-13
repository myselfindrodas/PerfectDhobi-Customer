package com.dhobi.perfectdhobi.data.model.EditAddressModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: Address
)