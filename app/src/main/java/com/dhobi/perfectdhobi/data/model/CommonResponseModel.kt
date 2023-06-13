package com.dhobi.perfectdhobi.data.model

import com.google.gson.annotations.SerializedName

class CommonResponseModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
) {
}