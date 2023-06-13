package com.dhobi.perfectdhobi.data.model.ProfileGetResponseModel


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("dob")
    val dob: String, // 1997-06-01T00:00:00.000Z
    @SerializedName("firstName")
    val firstName: String, // Test
    @SerializedName("gender")
    val gender: String, // men
    @SerializedName("lastName")
    val lastName: String, // test1
    @SerializedName("profileImage")
    val profileImage: Any? // null
)