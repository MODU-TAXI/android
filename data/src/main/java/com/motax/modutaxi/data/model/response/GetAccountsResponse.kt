package com.motax.modutaxi.data.model.response

data class GetAccountsResponse(
    val accounts: List<AccountItem>
)

data class AccountItem(
    val id: Long,
    val accountNumber: String,
    val bank: String,
    val ownerName: String
)
