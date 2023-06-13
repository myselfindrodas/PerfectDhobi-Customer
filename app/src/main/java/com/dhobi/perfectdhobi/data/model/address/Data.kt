package com.dhobi.perfectdhobi.data.model.address


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("addresses")
    val addresses: List<Addresse>
)