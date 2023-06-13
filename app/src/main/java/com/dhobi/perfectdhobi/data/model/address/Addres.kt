package com.dhobi.perfectdhobi.data.model.address


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Addres(
    @SerializedName("apartment_name")
    val apartmentName: String, // sd
    @SerializedName("area")
    val area: String, // asewq
    @SerializedName("is_primary")
    var isPrimary: Int = 0, // 0
) : Serializable