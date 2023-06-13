package com.dhobi.perfectdhobi.data.model.SlotBookingModel

import com.google.gson.annotations.SerializedName

data class SlotBookingRequestModel(
    @SerializedName("fullAddress")
    val fullAddress: String,
    @SerializedName("bookingType")
    val bookingType: String,
    @SerializedName("bookingOption")
    val bookingOption: String,
    @SerializedName("pickUpDayName")
    val pickUpDayName: String,
    @SerializedName("deliveryDayName")
    val deliveryDayName: String,
    @SerializedName("pickUpDate")
    val pickUpDate: String,
    @SerializedName("pickUpTime")
    val pickUpTime: String,
    @SerializedName("deliveryDate")
    val deliveryDate: String,
    @SerializedName("deliveryTime")
    val deliveryTime: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)

