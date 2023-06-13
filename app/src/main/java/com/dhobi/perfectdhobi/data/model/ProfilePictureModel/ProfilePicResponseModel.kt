package com.dhobi.perfectdhobi.data.model.ProfilePictureModel


import com.google.gson.annotations.SerializedName

data class ProfilePicResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Profile image updated successfully!
    @SerializedName("status")
    val status: Boolean // true
)