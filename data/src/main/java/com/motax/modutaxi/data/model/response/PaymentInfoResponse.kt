package com.motax.modutaxi.data.model.response

data class PaymentInfoResponse(
    val accountNumber: String,
    val bank: String,
    val ownerName: String,
    val totalCharge: Int,
    val status: String
)
