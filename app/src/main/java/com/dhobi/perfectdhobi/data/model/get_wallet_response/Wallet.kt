package com.dhobi.perfectdhobi.data.model.get_wallet_response


import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount")
    val amount: Amount,
    @SerializedName("_id")
    val id: String // 6479dc85187735700f755086
)