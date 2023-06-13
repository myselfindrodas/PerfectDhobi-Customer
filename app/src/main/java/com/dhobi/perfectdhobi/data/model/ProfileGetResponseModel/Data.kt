package com.dhobi.perfectdhobi.data.model.ProfileGetResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("userData")
    val userData: UserData
)