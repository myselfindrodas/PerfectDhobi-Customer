package com.dhobi.perfectdhobi.data.model.notificationmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("notifications")
    val notifications: List<Notification>
)