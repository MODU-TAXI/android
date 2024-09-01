package com.motax.modutaxi.domain.model

data class RegisterAccountData(
    val id: Long,
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)
