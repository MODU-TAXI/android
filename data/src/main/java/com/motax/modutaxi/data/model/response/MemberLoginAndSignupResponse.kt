package com.motax.modutaxi.data.model.response

import com.google.gson.annotations.SerializedName

data class MemberLoginAndSignupResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
