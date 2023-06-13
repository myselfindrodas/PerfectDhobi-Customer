package com.dhobi.perfectdhobi.data.model.FAQmodel


import com.dhobi.perfectdhobi.data.model.HelpandSupportModel.HelpAndSupport
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("faq")
    val faq: List<HelpAndSupport>
)