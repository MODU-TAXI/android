package com.motax.modutaxi.data.model.response

data class RegisterAccountResponse(
    val id: Long,
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)
