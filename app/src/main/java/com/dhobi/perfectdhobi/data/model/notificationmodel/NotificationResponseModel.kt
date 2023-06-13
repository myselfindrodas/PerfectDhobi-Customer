package com.dhobi.perfectdhobi.data.model.notificationmodel


import com.google.gson.annotations.SerializedName

data class NotificationResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Data available!
    @SerializedName("status")
    val status: Boolean // true
)