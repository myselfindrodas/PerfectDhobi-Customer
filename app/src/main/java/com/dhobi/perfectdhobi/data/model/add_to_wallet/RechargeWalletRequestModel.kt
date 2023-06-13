package com.dhobi.perfectdhobi.data.model.add_to_wallet

import com.google.gson.annotations.SerializedName

data class RechargeWalletRequestModel(
    @SerializedName("amount")
    val amount: String
)
