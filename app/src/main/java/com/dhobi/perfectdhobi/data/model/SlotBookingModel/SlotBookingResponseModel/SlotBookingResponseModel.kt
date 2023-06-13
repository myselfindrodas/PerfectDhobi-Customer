package com.dhobi.perfectdhobi.data.model.SlotBookingModel.SlotBookingResponseModel


import com.google.gson.annotations.SerializedName

data class SlotBookingResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Slot has been booked !
    @SerializedName("status")
    val status: Boolean // true
)