package com.motax.modutaxi.presentation.ui.main.chat.model

import com.motax.modutaxi.presentation.util.Bank

data class UiAccountItem(
    val bank : Bank = Bank.EMPTY,
    val account: String = "",
    val selectAccount: (Bank, String) -> Unit
)
