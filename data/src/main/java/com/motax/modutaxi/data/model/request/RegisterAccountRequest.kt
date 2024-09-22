package com.motax.modutaxi.data.model.request

data class RegisterAccountRequest(
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)
