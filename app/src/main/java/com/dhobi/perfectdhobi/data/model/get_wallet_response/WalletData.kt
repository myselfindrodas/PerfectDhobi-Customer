package com.dhobi.perfectdhobi.data.model.get_wallet_response


import com.google.gson.annotations.SerializedName

data class WalletData(
    @SerializedName("wallet")
    val wallet: Wallet
)