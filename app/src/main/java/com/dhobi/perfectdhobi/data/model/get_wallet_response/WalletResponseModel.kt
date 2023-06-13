package com.dhobi.perfectdhobi.data.model.get_wallet_response


import com.google.gson.annotations.SerializedName

data class WalletResponseModel(
    @SerializedName("data")
    val `data`: WalletData,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data available!
    @SerializedName("status")
    val status: Boolean // true
)