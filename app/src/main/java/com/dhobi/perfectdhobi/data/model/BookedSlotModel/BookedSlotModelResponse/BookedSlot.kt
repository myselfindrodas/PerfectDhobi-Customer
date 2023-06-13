package com.dhobi.perfectdhobi.data.model.BookedSlotModel.BookedSlotModelResponse


import com.google.gson.annotations.SerializedName

data class BookedSlot(
    @SerializedName("altId")
    val altId: String, // #LS-1686046434834
    @SerializedName("bookingOption")
    val bookingOption: String, // none
    @SerializedName("bookingType")
    val bookingType: String, // single
    @SerializedName("bulkQuantity")
    val bulkQuantity: Any?, // null
    @SerializedName("createdAt")
    val createdAt: String, // 2023-06-09T09:44:39.892Z
    @SerializedName("currentStatus")
    val currentStatus: String, // booked
    @SerializedName("deliveryBoyId")
    val deliveryBoyId: Any?, // null
    @SerializedName("deliveryDate")
    val deliveryDate: String?, // 2023-06-12T00:00:00.000Z
    @SerializedName("deliveryDayName")
    val deliveryDayName: String?, // Wednesday
    @SerializedName("deliveryDayNameOne")
    val deliveryDayNameOne: Any?, // null
    @SerializedName("deliveryDayNameTwo")
    val deliveryDayNameTwo: Any?, // null
    @SerializedName("deliveryTime")
    val deliveryTime: String, // 9:00 AM
    @SerializedName("deliveryTimeOne")
    val deliveryTimeOne: Any?, // null
    @SerializedName("deliveryTimeTwo")
    val deliveryTimeTwo: Any?, // null
    @SerializedName("fullAddress")
    val fullAddress: String, // Barasat ,Kolkata 
    @SerializedName("_id")
    val id: String, // 6482f4872c9c5858c848e4d3
    @SerializedName("isActive")
    val isActive: Boolean, // true
    @SerializedName("isDeleted")
    val isDeleted: Boolean, // false
    @SerializedName("latitude")
    val latitude: String, // 22.7202681
    @SerializedName("longitude")
    val longitude: String, // 88.48630770000001
    @SerializedName("pickUpDate")
    val pickUpDate: String?, // 2023-06-09T00:00:00.000Z
    @SerializedName("pickUpDayName")
    val pickUpDayName: String?, // Tuesday
    @SerializedName("pickUpDayNameOne")
    val pickUpDayNameOne: Any?, // null
    @SerializedName("pickUpDayNameTwo")
    val pickUpDayNameTwo: Any?, // null
    @SerializedName("pickUpTime")
    val pickUpTime: String, // 6:30 AM
    @SerializedName("pickUpTimeOne")
    val pickUpTimeOne: Any?, // null
    @SerializedName("pickUpTimeTwo")
    val pickUpTimeTwo: Any?, // null
    @SerializedName("updatedAt")
    val updatedAt: String, // 2023-06-09T09:44:39.892Z
    @SerializedName("userId")
    val userId: String, // 647dbd78ad8c9aa6a1b63261
    @SerializedName("__v")
    val v: Int // 0
)