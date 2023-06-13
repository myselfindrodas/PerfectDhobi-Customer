package com.dhobi.perfectdhobi.data.model.BookedSlotModel.BookedSlotModelResponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bookedSlots")
    val bookedSlots: List<BookedSlot>
)