package com.motax.modutaxi.domain.model

data class AccountData(
    val accounts : List<AccountDataItem>
)

data class AccountDataItem(
    val id: Long,
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)
