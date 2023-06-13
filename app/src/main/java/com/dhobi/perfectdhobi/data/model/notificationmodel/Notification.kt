package com.dhobi.perfectdhobi.data.model.notificationmodel


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("createdAt")
    val createdAt: String, // 2023-05-04T12:44:06.582Z
    @SerializedName("description")
    val description: String, // Lorem ipsum doler sit amet
    @SerializedName("_id")
    val id: String, // 64634a698a450cc28a73a881
    @SerializedName("isRead")
    val isRead: Boolean, // false
    @SerializedName("title")
    val title: String // Test title 1
)