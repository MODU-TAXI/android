package com.motax.modutaxi.data.model.request

data class SignUpRequest(
    val key: String,
    val name: String,
    val gender: String,
    val phoneNumber: String,
    val fcmToken: String
)
