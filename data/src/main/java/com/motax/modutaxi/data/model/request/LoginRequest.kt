package com.motax.modutaxi.data.model.request

data class LoginRequest(
    val accessToken: String,
    val fcmToken: String
)
