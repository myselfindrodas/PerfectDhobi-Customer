package com.dhobi.perfectdhobi.data.model.ProfileUpdateModel

import com.google.gson.annotations.SerializedName

data class ProfileUpdateRequestModel (
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("gender")
    val gender: String
)