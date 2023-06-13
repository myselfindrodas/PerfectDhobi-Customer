package com.dhobi.perfectdhobi.data.model.get_wallet_response


import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("$"+"numberDecimal")
    val numberDecimal: String // 200
)