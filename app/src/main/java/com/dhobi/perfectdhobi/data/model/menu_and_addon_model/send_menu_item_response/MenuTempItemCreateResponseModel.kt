package com.dhobi.perfectdhobi.data.model.menu_and_addon_model.send_menu_item_response


import com.google.gson.annotations.SerializedName

data class MenuTempItemCreateResponseModel(
    @SerializedName("data")
    val `data`: TempOrderIdData,
    @SerializedName("errors")
    val errors: Any?, // null
    @SerializedName("message")
    val message: String, // Order has been temporarily stored!
    @SerializedName("status")
    val status: Boolean // true
)