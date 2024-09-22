package com.motax.modutaxi.data.model.response

data class AccountResponse(
    val accounts : List<AccountResponseItem>
)

data class AccountResponseItem(
    val id: Long,
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)