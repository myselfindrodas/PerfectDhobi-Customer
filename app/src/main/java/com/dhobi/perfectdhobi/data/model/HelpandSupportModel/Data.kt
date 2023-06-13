package com.dhobi.perfectdhobi.data.model.HelpandSupportModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("helpAndSupports")
    val helpAndSupports: List<HelpAndSupport>
)