package com.motax.modutaxi.data.model.request

data class SmsConfirmRequest(
    val key: String,
    val phoneNumber: String,
    val certificationCode: String
)
