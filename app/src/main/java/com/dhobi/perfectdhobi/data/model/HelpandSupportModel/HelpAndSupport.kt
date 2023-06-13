package com.dhobi.perfectdhobi.data.model.HelpandSupportModel


import com.google.gson.annotations.SerializedName

data class HelpAndSupport(
    @SerializedName("answer")
    val answer: String, // test test
    @SerializedName("createdAt")
    val createdAt: String, // 2023-05-24T11:47:17.760Z
    @SerializedName("_id")
    val id: String, // 646df945cbb15cd37cda0cae
    @SerializedName("isActive")
    val isActive: Boolean, // true
    @SerializedName("isDeleted")
    val isDeleted: Boolean, // false
    @SerializedName("question")
    val question: String, // lorem
    @SerializedName("type")
    val type: String, // customer
    @SerializedName("updatedAt")
    val updatedAt: String, // 2023-05-24T11:47:17.760Z
    @SerializedName("__v")
    val v: Int, // 0
    var isExpanded: Boolean=false // null

)