package com.motax.modutaxi.domain.model

data class PaymentInfoData(
    val accountNumber: String,
    val bank: String,
    val ownerName: String,
    val totalCharge: Int,
    val status: String
)
